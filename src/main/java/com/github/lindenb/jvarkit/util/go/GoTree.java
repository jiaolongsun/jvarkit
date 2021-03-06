package com.github.lindenb.jvarkit.util.go;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import com.github.lindenb.jvarkit.io.IOUtils;

public class GoTree
	{
	private static final String NS="http://www.geneontology.org/dtds/go.dtd#";
	private static final String PREFIX="http://www.geneontology.org/go#";
	public static interface Term
		{
		public String getAcn();
		public String getLabel();
		public Set<Term> getParents();
		public Set<Term> getChildren();
		public boolean isDescendantOf(String acn);
		public boolean hasDescendant(String acn);

		//public Set<Term> getAllParents();
		//public Set<Term> getAllChildren();
		}
	
	private GoTree()
		{
		}
	
	public int size()
		{
		return uri2term.size();
		}
	
	public List<? extends Term> getTerms() 
		{
		return new ArrayList<Term>(uri2term.values());
		}
	
	public void dump()
		{
		for(String s: uri2term.keySet())
			{
			Term t=uri2term.get(s);
			System.out.println(s+" "+t.getAcn()+" "+t.getLabel()+" "+t.getChildren()+" "+t.getParents());
			}
		}
	
	
	private HashMap<String, TermImpl> uri2term=new HashMap<String, TermImpl>();
	
	private class TermImpl implements Term
		{
		String accession;
		String name;
		Set<String> parents=new HashSet<String>();
		Set<String> children=new HashSet<String>();
		
		@Override
		public String getAcn() {
			return accession;
			}
		@Override
		public String getLabel() {
			return name;
			}	
		
		private Set<Term> convert(Set<String> S1)
			{
			Set<Term> S2=new HashSet<Term>(S1.size());
			for(String s:S1)
				{
				Term t=uri2term.get(s);
				if(t==null) continue;
				S2.add(t);
				}
			return S2;
			}
		
		@Override
		public Set<Term> getChildren()
			{
			return convert(children);
			}
		
		@Override
		public Set<Term> getParents()
			{
			return convert(parents);
			}
		/*
		private void _getAllChildren(Set<String> seen)
			{
			for(String s:this.children)
				{
				TermImpl t=uri2term.get(s);
				if(t==null) continue;
				seen.add(s);
				t._getAllChildren(seen);
				}
			}
		
		private void _getAllParents(Set<String> seen)
			{
			for(String s:this.parents)
				{
				TermImpl t=uri2term.get(s);
				if(t==null) continue;
				seen.add(s);
				t._getAllParents(seen);
				}
			}
		
		
		public Set<Term> getAllChildren()
			{
			Set<String> seen=new HashSet<String>();
			_getAllChildren(seen);
			return convert(seen);
			}
		
		//@Override
		public Set<Term> getAllParents()
			{
			Set<String> seen=new HashSet<String>();
			_getAllParents(seen);
			return convert(seen);
			}*/
		
		@Override
		public boolean isDescendantOf(String parentAcn)
			{
			if(parentAcn.equals(this.accession)) return true;
			if(!parentAcn.startsWith(PREFIX)) parentAcn=PREFIX+parentAcn;
			for(String p:this.parents)
				{
				TermImpl pNode=uri2term.get(p);
				if(pNode==null || pNode==this) continue;
				if(pNode.isDescendantOf(parentAcn)) return true;
				}
			return false;
			}
		
		@Override
		public boolean hasDescendant(String descendantAcn)
			{
			if(descendantAcn.equals(this.accession)) return true;
			if(!descendantAcn.startsWith(PREFIX)) descendantAcn=PREFIX+descendantAcn;
			for(String p:this.children)
				{
				TermImpl pNode=uri2term.get(p);
				if(pNode==null || pNode==this) continue;
				if(pNode.hasDescendant(descendantAcn)) return true;
				}
			return false;
			}
		
		@Override
		public int hashCode()
			{
			return accession.hashCode();
			}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			TermImpl other = (TermImpl) obj;
			return accession.equals(other.accession);
			}
		@Override
		public String toString() {
			return accession;
			}
		}
	
	public Term getTermByAccession(String s)
		{
		if(!s.startsWith(PREFIX)) s=PREFIX+s;
		return (Term)uri2term.get(s);
		}
		
	private static final QName rdfAbout=new QName("http://www.w3.org/1999/02/22-rdf-syntax-ns#","about","rdf");
	private static final QName rdfRsrc=new QName("http://www.w3.org/1999/02/22-rdf-syntax-ns#","resource","rdf");
	private void parseTerm(StartElement root,XMLEventReader r) throws IOException,XMLStreamException
		{
		
		Attribute aboutAtt=root.getAttributeByName(rdfAbout);
		if(aboutAtt==null)
			{
			throw new IOException("no rdf:about");
			}
		TermImpl term=uri2term.get(aboutAtt.getValue());
		
		if(term==null)
			{
			term=new TermImpl();
			term.accession=aboutAtt.getValue();
			if(term.accession.startsWith(PREFIX))
				{
				term.accession=term.accession.substring(PREFIX.length());
				}
			term.name=term.accession;
			uri2term.put(aboutAtt.getValue(),term);
			}
		while(r.hasNext())
			{
			XMLEvent evt=r.nextEvent();
			if(evt.isStartElement())
				{
				StartElement E=evt.asStartElement();
				QName qN=E.getName();
				if( NS.equals(qN.getNamespaceURI()))
					{
					if(qN.getLocalPart().equals("accession"))
						{
						term.accession=r.getElementText();
						}
					else if(qN.getLocalPart().equals("name"))
						{
						term.name=r.getElementText();
						}
					else if(qN.getLocalPart().equals("is_a"))
						{
						Attribute rsrc=E.getAttributeByName(rdfRsrc);
						if(rsrc==null) throw new IOException("att missing "+rdfRsrc+" for "+aboutAtt.getValue());
							
						String parentUri=rsrc.getValue();
						term.parents.add(parentUri);
						TermImpl parentTerm=this.uri2term.get(parentUri);
						if(parentTerm==null)
							{
							parentTerm=new TermImpl();
							parentTerm.accession=parentUri;
							if(parentTerm.accession.startsWith(PREFIX))
								{
								parentTerm.accession=parentTerm.accession.substring(PREFIX.length());
								}
							parentTerm.name=parentTerm.accession;
							uri2term.put(parentUri,parentTerm);
							}
						parentTerm.children.add(aboutAtt.getValue());							
						}
					}
				
				}
			else if(evt.isEndElement())
				{
				EndElement E=evt.asEndElement();
				QName qN=E.getName();
				if(qN.getLocalPart().equals("term") && NS.equals(qN.getNamespaceURI()))
					{
					break;
					}
				}
			}
	
		}	
	
	public static GoTree parse(Reader xmlIn) throws IOException,XMLStreamException
		{
		GoTree tree=new GoTree();
		XMLInputFactory fact=XMLInputFactory.newFactory();
		fact.setProperty(XMLInputFactory.IS_NAMESPACE_AWARE, Boolean.TRUE);
		XMLEventReader r=fact.createXMLEventReader(xmlIn);
		while(r.hasNext())
			{
			XMLEvent evt=r.nextEvent();
			if(evt.isStartElement())
				{
				StartElement E=evt.asStartElement();
				QName qN=E.getName();
				if(qN.getLocalPart().equals("term") && NS.equals(qN.getNamespaceURI()))
					{
					tree.parseTerm(E,r);
					}
				}
			}
		r.close();
		return tree;
		}
	
	public static GoTree parse(File file) throws IOException,XMLStreamException
		{
		BufferedReader r=IOUtils.openFileForBufferedReading(file);
		GoTree t=parse(r);
		r.close();
		return t;
		}
	public static GoTree parse(String uri) throws IOException,XMLStreamException
		{
		BufferedReader r=IOUtils.openURIForBufferedReading(uri);
		GoTree t=parse(r);
		r.close();
		return t;
		}
	
	}
