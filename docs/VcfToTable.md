# VcfToTable

convert a vcf to a table, to ease display in the terminal


## Usage

```
Usage: vcf2table [options] Files
  Options:
    --color, --colors
      [20170808] Print Terminal ANSI colors.
      Default: false
    --format
      [20171020] output format.
      Default: text
      Possible Values: [text, html]
    -h, --help
      print help and exit
    --helpFormat
      What kind of help
      Possible Values: [usage, markdown, xml]
    --hideAlleles
      [20170808] hide Alleles table.
      Default: false
    --hideFilters
      [20170808] hide Filters table.
      Default: false
    -g, --hideGenotypes
      Hide All genotypes
      Default: false
    -hr, --hideHomRefs
      Hide HOM_REF genotypes
      Default: false
    --hideInfo
      [20170808] hide INFO table.
      Default: false
    -nc, --hideNoCalls
      Hide NO_CALL genotypes
      Default: false
    --hidePredictions
      [20170808] hide SNPEFF/VEP table.
      Default: false
    -L, -limit, --limit
      Limit the number of output variant. '-1' == ALL/No limit.
      Default: -1
    --no-html-header
      [20171023] ignore html header for HTML output.
      Default: false
    -o, --output
      Output file. Optional . Default: stdout
    -p, --ped, --pedigree
      Optional Pedigree file:A pedigree is a text file delimited with tabs. No 
      header. Columns are (1) Family (2) Individual-ID (3) Father Id or '0' 
      (4) Mother Id or '0' (5) Sex : 1 male/2 female / 0 unknown (6) Status : 
      0 unaffected, 1 affected,-9 unknown  If undefined, this tool will try to 
      get the pedigree from the header.
    --url
      A custom URL for a web browser. The following words will be replaced by 
      their values: ${CHROM}, ${START}, ${END}. For example for IGV that would 
      be: 'http://localhost:60151/goto?locus=${CHROM}%3A${START}-${END}' (see 
      http://software.broadinstitute.org/software/igv/book/export/html/189) 
    --version
      print version and exit
    -H
      Print Header
      Default: false

```


## Keywords

 * vcf
 * table
 * visualization


## Compilation

### Requirements / Dependencies

* java [compiler SDK 1.8](http://www.oracle.com/technetwork/java/index.html) (**NOT the old java 1.7 or 1.6**) and avoid OpenJdk, use the java from Oracle. Please check that this java is in the `${PATH}`. Setting JAVA_HOME is not enough : (e.g: https://github.com/lindenb/jvarkit/issues/23 )
* GNU Make >= 3.81
* curl/wget
* git
* xsltproc http://xmlsoft.org/XSLT/xsltproc2.html (tested with "libxml 20706, libxslt 10126 and libexslt 815")


### Download and Compile

```bash
$ git clone "https://github.com/lindenb/jvarkit.git"
$ cd jvarkit
$ make vcf2table
```

The *.jar libraries are not included in the main jar file, [so you shouldn't move them](https://github.com/lindenb/jvarkit/issues/15#issuecomment-140099011 ).
The required libraries will be downloaded and installed in the `dist` directory.

Experimental: you can also create a [fat jar](https://stackoverflow.com/questions/19150811/) which contains classes from all the libraries, on which your project depends (it's bigger). Those fat-jar are generated by adding `standalone=yes` to the gnu make command, for example ` make vcf2table standalone=yes`.

### edit 'local.mk' (optional)

The a file **local.mk** can be created edited to override/add some definitions.

For example it can be used to set the HTTP proxy:

```
http.proxy.host=your.host.com
http.proxy.port=124567
```
## Source code 

[https://github.com/lindenb/jvarkit/tree/master/src/main/java/com/github/lindenb/jvarkit/tools/misc/VcfToTable.java](https://github.com/lindenb/jvarkit/tree/master/src/main/java/com/github/lindenb/jvarkit/tools/misc/VcfToTable.java)


<details>
<summary>Git History</summary>

```
Wed Nov 8 09:00:21 2017 +0100 ; changed igv to custom url ; https://github.com/lindenb/jvarkit/commit/7972f506165e45c3b1300aecd00d1109b52d57dc
Tue Nov 7 16:33:40 2017 +0100 ; Hyperlinks to IGV ; https://github.com/lindenb/jvarkit/commit/b41b49dfc9e1948eb76085c343b69a4ac0520f16
Mon Oct 30 17:26:13 2017 +0100 ; updating vcf server, vcfpolyx, answer biostars ; https://github.com/lindenb/jvarkit/commit/428a7ea5a848d974fa2e09555ad94de014febdde
Sat Oct 28 18:04:34 2017 +0200 ; vcf server ; https://github.com/lindenb/jvarkit/commit/1ba12c148566055ec896ddec9b7c2d7674cf8236
Fri Oct 27 18:13:18 2017 +0200 ; cont vcf server ; https://github.com/lindenb/jvarkit/commit/abc4d04da94e86f7d4955e24ffdec9632afd7bdc
Fri Oct 27 15:15:11 2017 +0200 ; adding vcf server and https://www.biostars.org/p/279942/#280255 ; https://github.com/lindenb/jvarkit/commit/3eabba0b8c06b88f90193f958e47a725d105216a
Thu Oct 26 17:33:55 2017 +0200 ; added urls to vcf2table ; https://github.com/lindenb/jvarkit/commit/6afd4dbbd062d175325f1cda7006f112e35a719c
Mon Oct 23 16:48:52 2017 +0200 ; vcf2table html output ; https://github.com/lindenb/jvarkit/commit/a30f3c2551dd1809ed7c311e8e276bfab4fb166a
Sun Oct 22 21:49:32 2017 +0200 ; xml + vcf2table ; https://github.com/lindenb/jvarkit/commit/cbd605b37c5b360683935779e46cc37fcc667fee
Wed Aug 23 06:06:58 2017 +0200 ; vcf2table check genotypes ; https://github.com/lindenb/jvarkit/commit/1ff08305fab2b5b099a04ce8af13c6bac8fdee9c
Tue Aug 8 17:07:46 2017 +0200 ; cont ; https://github.com/lindenb/jvarkit/commit/2d33719edc69a979a2b6366351ca6f0b59959755
Mon Aug 7 09:53:19 2017 +0200 ; fixed unicode problems after https://github.com/lindenb/jvarkit/issues/82 ; https://github.com/lindenb/jvarkit/commit/68254c69b027a9ce81d8b211447f1c0bf02dc626
Thu Jul 13 20:16:36 2017 +0200 ; cont ; https://github.com/lindenb/jvarkit/commit/85b6c9c196e9a065dfd47bee37fe50238af41660
Thu Jun 29 17:31:10 2017 +0200 ; cont ; https://github.com/lindenb/jvarkit/commit/1aac040bed918f89b1ce68b2c8f7a0c6d5cfddd0
Wed Jun 21 15:32:51 2017 +0200 ; zuuut ; https://github.com/lindenb/jvarkit/commit/f726d8cf6a35eff8b022185f1d7176bc76eb3f9f
Wed Jun 21 15:27:13 2017 +0200 ; cont ; https://github.com/lindenb/jvarkit/commit/034f57d0e8d0399c12b290385d89e498e6138e1d
Tue Jun 13 20:17:49 2017 +0200 ; cont ; https://github.com/lindenb/jvarkit/commit/dee134846758d645476f9388d2ae1a13f53b741f
Fri Jun 9 09:31:08 2017 +0200 ; cont ; https://github.com/lindenb/jvarkit/commit/756ee58a387e69115b7d0a79bd16daa5df2266fc
Mon May 29 16:53:42 2017 +0200 ; moved to docs ; https://github.com/lindenb/jvarkit/commit/6c0535d7add884e75b424af89a4f00aff6fae75f
Wed May 17 09:23:56 2017 +0200 ; vcf2table ; https://github.com/lindenb/jvarkit/commit/86bfd0b9d8a2f3606560ad41822e6e20f112095a
Fri May 12 18:07:46 2017 +0200 ; cont ; https://github.com/lindenb/jvarkit/commit/ca96bce803826964a65de33455e5231ffa6ea9bd
Fri May 12 10:49:17 2017 +0200 ; pedigree in vcf2table ; https://github.com/lindenb/jvarkit/commit/8873f4bd7904964d186c926e78956b5ea5602952
Fri May 12 10:45:17 2017 +0200 ; pedigree in vcf2table ; https://github.com/lindenb/jvarkit/commit/cd2b0ab7842fa286b27099455c75a7f885e07386
Thu May 11 20:58:29 2017 +0200 ; vcf2table ; https://github.com/lindenb/jvarkit/commit/a1aa58df16db6cee97297c0bd505cc3650c1b638
Thu May 11 20:56:40 2017 +0200 ; vcf2table ; https://github.com/lindenb/jvarkit/commit/48e7523e800a0300c40dd6d0bac4b9d4fd43bc84
Thu May 11 17:32:23 2017 +0200 ; vcf2table ; https://github.com/lindenb/jvarkit/commit/453621542766e5bae3498f1641168c277a693183
```

</details>

## Contribute

- Issue Tracker: [http://github.com/lindenb/jvarkit/issues](http://github.com/lindenb/jvarkit/issues)
- Source Code: [http://github.com/lindenb/jvarkit](http://github.com/lindenb/jvarkit)

## License

The project is licensed under the MIT license.

## Citing

Should you cite **vcf2table** ? [https://github.com/mr-c/shouldacite/blob/master/should-I-cite-this-software.md](https://github.com/mr-c/shouldacite/blob/master/should-I-cite-this-software.md)

The current reference is:

[http://dx.doi.org/10.6084/m9.figshare.1425030](http://dx.doi.org/10.6084/m9.figshare.1425030)

> Lindenbaum, Pierre (2015): JVarkit: java-based utilities for Bioinformatics. figshare.
> [http://dx.doi.org/10.6084/m9.figshare.1425030](http://dx.doi.org/10.6084/m9.figshare.1425030)


## Example 

```
$ cat input.ped

FAM	M10475	0	0	1	1
FAM	M10478	0	0	2	0
FAM	M10500	M10475	M10478	2	1


$ curl -s "https://raw.githubusercontent.com/arq5x/gemini/master/test/test.region.vep.vcf" | java -jar dist/vcf2table.jar -H -p input.ped

 
INFO
+-----------------+---------+-------+---------------------------------------------------------------------------------------------------------------------------------------------------------+
| ID              | Type    | Count | Description                                                                                                                                             |
+-----------------+---------+-------+---------------------------------------------------------------------------------------------------------------------------------------------------------+
| AC              | Integer |       | Allele count in genotypes, for each ALT allele, in the same order as listed                                                                             |
| AF              | Float   |       | Allele Frequency, for each ALT allele, in the same order as listed                                                                                      |
| AN              | Integer | 1     | Total number of alleles in called genotypes                                                                                                             |
| BaseQRankSum    | Float   | 1     | Z-score from Wilcoxon rank sum test of Alt Vs. Ref base qualities                                                                                       |
| CSQ             | String  |       | Consequence type as predicted by VEP. Format: Consequence|Codons|Amino_acids|Gene|SYMBOL|Feature|EXON|PolyPhen|SIFT|Protein_position|BIOTYPE|ALLELE_NUM |
| DP              | Integer | 1     | Approximate read depth; some reads may have been filtered                                                                                               |
| DS              | Flag    | 0     | Were any of the samples downsampled?                                                                                                                    |
| Dels            | Float   | 1     | Fraction of Reads Containing Spanning Deletions                                                                                                         |
| FS              | Float   | 1     | Phred-scaled p-value using Fisher's exact test to detect strand bias                                                                                    |
| HRun            | Integer | 1     | Largest Contiguous Homopolymer Run of Variant Allele In Either Direction                                                                                |
| HaplotypeScore  | Float   | 1     | Consistency of the site with at most two segregating haplotypes                                                                                         |
| InbreedingCoeff | Float   | 1     | Inbreeding coefficient as estimated from the genotype likelihoods per-sample when compared against the Hardy-Weinberg expectation                       |
| MQ              | Float   | 1     | RMS Mapping Quality                                                                                                                                     |
| MQ0             | Integer | 1     | Total Mapping Quality Zero Reads                                                                                                                        |
| MQRankSum       | Float   | 1     | Z-score From Wilcoxon rank sum test of Alt vs. Ref read mapping qualities                                                                               |
| QD              | Float   | 1     | Variant Confidence/Quality by Depth                                                                                                                     |
| ReadPosRankSum  | Float   | 1     | Z-score from Wilcoxon rank sum test of Alt vs. Ref read position bias                                                                                   |
+-----------------+---------+-------+---------------------------------------------------------------------------------------------------------------------------------------------------------+

FORMAT
+----+---------+-------+----------------------------------------------------------------------------------------+
| ID | Type    | Count | Description                                                                            |
+----+---------+-------+----------------------------------------------------------------------------------------+
| AD | Integer |       | Allelic depths for the ref and alt alleles in the order listed                         |
| DP | Integer | 1     | Approximate read depth (reads with MQ=255 or with bad mates are filtered)              |
| GQ | Integer | 1     | Genotype Quality                                                                       |
| GT | String  | 1     | Genotype                                                                               |
| PL | Integer |       | Normalized, Phred-scaled likelihoods for genotypes as defined in the VCF specification |
+----+---------+-------+----------------------------------------------------------------------------------------+


Dict
+-----------------------+-----------+------+
| Name                  | Length    | AS   |
+-----------------------+-----------+------+
| chr1                  | 249250621 | hg19 |
(...)
| chrX                  | 155270560 | hg19 |
| chrY                  | 59373566  | hg19 |
+-----------------------+-----------+------+

Samples
+--------+---------+--------+--------+--------+------------+
| Family | Sample  | Father | Mother | Sex    | Status     |
+--------+---------+--------+--------+--------+------------+
| FAM    | M10475  |        |        | male   | affected   |
| FAM    | M10478  |        |        | female | unaffected |
| FAM    | M10500  | M10475 | M10478 | female | affected   |
| FAM    | M128215 | M10500 |        | male   | unaffected |
+--------+---------+--------+--------+--------+------------+

>>chr1/10001/T (n 1)
 Variant
 +--------+--------------------+
 | Key    | Value              |
 +--------+--------------------+
 | CHROM  | chr1               |
 | POS    | 10001              |
 | end    | 10001              |
 | ID     | .                  |
 | REF    | T                  |
 | ALT    | TC                 |
 | QUAL   | 175.91000000000003 |
 | FILTER |                    |
 | Type   | INDEL              |
 +--------+--------------------+
 Alleles
 +-----+-----+-----+-------+--------+----+----+-----+-------------+---------------+---------+-----------+
 | Idx | REF | Sym | Bases | Length | AC | AN | AF  | AC_affected | AC_unaffected | AC_male | AC_female |
 +-----+-----+-----+-------+--------+----+----+-----+-------------+---------------+---------+-----------+
 | 0   | *   |     | T     | 1      | 4  | 8  | 0.5 | 2           | 1             | 1       | 2         |
 | 1   |     |     | TC    | 2      | 4  | 8  | 0.5 | 2           | 1             | 1       | 2         |
 +-----+-----+-----+-------+--------+----+----+-----+-------------+---------------+---------+-----------+
 INFO
 +----------------+-------+----------+
 | key            | Index | Value    |
 +----------------+-------+----------+
 | AC             |       | 4        |
 | AF             |       | 0.50     |
 | AN             |       | 8        |
 | BaseQRankSum   |       | 4.975    |
 | DP             |       | 76       |
 | FS             |       | 12.516   |
 | HRun           |       | 0        |
 | HaplotypeScore |       | 218.6157 |
 | MQ             |       | 35.31    |
 | MQ0            |       | 0        |
 | MQRankSum      |       | -0.238   |
 | QD             |       | 2.31     |
 | ReadPosRankSum |       | 2.910    |
 +----------------+-------+----------+
 VEP
 +--------------------------+------+----------------+------------+-----------------+--------+------------------+-----------------------------------------------+-------------+---------+-----------------+----------------------+
 | PolyPhen                 | EXON | SIFT           | ALLELE_NUM | Gene            | SYMBOL | Protein_position | Consequence                                   | Amino_acids | Codons  | Feature         | BIOTYPE              |
 +--------------------------+------+----------------+------------+-----------------+--------+------------------+-----------------------------------------------+-------------+---------+-----------------+----------------------+
 | probably_damaging(0.956) | 8/9  | deleterious(0) | 1          | ENSG00000102967 | DHODH  | 346/395          | missense_variant                              | R/W         | Cgg/Tgg | ENST00000219240 | protein_coding       |
 |                          | 3/4  |                | 1          | ENSG00000102967 | DHODH  |                  | non_coding_exon_variant&nc_transcript_variant |             |         | ENST00000571392 | retained_intron      |
 |                          |      |                | 1          | ENSG00000102967 | DHODH  |                  | downstream_gene_variant                       |             |         | ENST00000572003 | retained_intron      |
 |                          |      |                | 1          | ENSG00000102967 | DHODH  |                  | downstream_gene_variant                       |             |         | ENST00000573843 | retained_intron      |
 |                          |      |                | 1          | ENSG00000102967 | DHODH  |                  | downstream_gene_variant                       |             |         | ENST00000573922 | processed_transcript |
 |                          |      |                | 1          | ENSG00000102967 | DHODH  | -/193            | intron_variant                                |             |         | ENST00000574309 | protein_coding       |
 | probably_damaging(0.946) | 8/9  | deleterious(0) | 1          | ENSG00000102967 | DHODH  | 344/393          | missense_variant                              | R/W         | Cgg/Tgg | ENST00000572887 | protein_coding       |
 +--------------------------+------+----------------+------------+-----------------+--------+------------------+-----------------------------------------------+-------------+---------+-----------------+----------------------+
 Genotypes
 +---------+------+-------+----+----+-----+---------+
 | Sample  | Type | AD    | DP | GQ | GT  | PL      |
 +---------+------+-------+----+----+-----+---------+
 | M10475  | HET  | 10,2  | 15 | 10 | 0/1 | 25,0,10 |
 | M10478  | HET  | 10,4  | 16 | 5  | 0/1 | 40,0,5  |
 | M10500  | HET  | 10,10 | 21 | 7  | 0/1 | 111,0,7 |
 | M128215 | HET  | 15,5  | 24 | 0  | 0/1 | 49,0,0  |
 +---------+------+-------+----+----+-----+---------+
 TRIOS
 +-----------+-----------+-----------+-----------+----------+----------+-----------+
 | Father-ID | Father-GT | Mother-ID | Mother-GT | Child-ID | Child-GT | Incompat. |
 +-----------+-----------+-----------+-----------+----------+----------+-----------+
 | M10475    | 0/1       | M10478    | 0/1       | M10500   | 0/1      |           |
 +-----------+-----------+-----------+-----------+----------+----------+-----------+
<<chr1/10001/T n 1

(...)
```

### Html output:


```
$ java -jar dist/vcf2table.jar file.vcf --color --format html > out.html
```



