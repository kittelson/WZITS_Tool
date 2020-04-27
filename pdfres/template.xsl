<stylesheet xmlns="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format" version="1.0"
            xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <output encoding="UTF-8" indent="yes" method="xml" standalone="no" omit-xml-declaration="no"/>

    <template match="pdf-data">
        <xsl:variable name="fontSize">
            10pt
        </xsl:variable>
        <xsl:variable name="titleFontSize">
            14pt
        </xsl:variable>
        <xsl:variable name="headerFontSize">
            12pt
        </xsl:variable>
        <xsl:variable name="fontFamily">
            Helvetica
        </xsl:variable>
        <xsl:attribute name="font-family">
            <xsl:value-of select="$fontFamily"/>
        </xsl:attribute>
        <fo:root language="EN">
            <fo:layout-master-set>
                <fo:simple-page-master master-name="FREEVAL" page-height="280mm" page-width="220mm"
                                       margin-left="10mm" margin-right="10mm" margin="5mm">
                    <fo:region-body margin-top="20mm" margin-bottom="10mm"/>
                    <fo:region-before region-name="header" extent="15mm" display-align="before" precedence="true"/>
                    <fo:region-after region-name="footer" extent="25mm" display-align="after" precedence="true"/>
                </fo:simple-page-master>
            </fo:layout-master-set>
            <fo:page-sequence master-reference="FREEVAL">
                <fo:static-content flow-name="header">
                    <fo:table table-layout="fixed" width="100%" font-size="10pt" border-color="grey"
                              border-width="0.4mm" border-style="solid">
                        <fo:table-column column-width="proportional-column-width(20)"/>
                        <fo:table-column column-width="proportional-column-width(60)"/>
                        <fo:table-column column-width="proportional-column-width(20)"/>
                        <fo:table-body>
                            <fo:table-row>
                                <fo:table-cell text-align="left" display-align="center" padding-left="2mm">
                                    <fo:block>
                                        <fo:external-graphic src="{header/freevalLogo}" content-width="0.4in"/>
                                    </fo:block>
                                </fo:table-cell>
                                <fo:table-cell display-align="center" border-color="grey"
                                               border-width="0.4mm">
                                    <fo:block text-align="center" font-size="12pt" font-weight="bold" white-space-treatment="preserve">
                                        <value-of select="header/title1"/>
                                    </fo:block>
                                    <fo:block text-align="center" font-size="12pt" white-space-treatment="preserve">
                                        <value-of select="header/title2"/>
                                    </fo:block>
                                </fo:table-cell>
                                <fo:table-cell text-align="right" display-align="center"
                                               border-color="grey" border-width="0.4mm"
                                               padding-right="2mm" padding-top="1mm">
                                    <fo:block display-align="before">
                                        <fo:external-graphic src="{header/ncdotLogo}" content-width="0.4in"/>
                                    </fo:block>
                                </fo:table-cell>
                            </fo:table-row>
                        </fo:table-body>
                    </fo:table>
                </fo:static-content>


                <fo:static-content flow-name="footer">
                    <fo:table table-layout="fixed" width="100%" font-size="10pt" border-top-color="grey"
                              border-top-width="0.2mm" border-top-style="solid">
                        <fo:table-column column-width="proportional-column-width(18)"/>
                        <fo:table-column column-width="proportional-column-width(72)"/>
                        <fo:table-column column-width="proportional-column-width(10)"/>
                        <fo:table-body font-size="8pt">
                            <fo:table-row>
                                <fo:table-cell text-align="left" display-align="center" padding-top="2mm">
                                    <fo:block>
                                        <xsl:value-of select="header/programVersion"/>
                                    </fo:block>
                                </fo:table-cell>
                                 <fo:table-cell text-align="center" display-align="center" padding-top="2mm">
                                    <fo:block>
                                        <xsl:value-of select="header/seedFileName"/>
                                    </fo:block>
                                </fo:table-cell>
                                <fo:table-cell text-align="right" display-align="center" padding-top="2mm">
                                    <fo:block text-align="right">Page
                                        <fo:page-number/>
                                        of
                                        <fo:page-number-citation ref-id="end-of-document"/>
                                    </fo:block>
                                </fo:table-cell>
                            </fo:table-row>
                        </fo:table-body>
                    </fo:table>

                </fo:static-content>

                <fo:flow flow-name="xsl-region-body" border-collapse="collapse" reference-orientation="0">
                    <fo:block>
                        <for-each select="body/child::node()">
                            <choose>
                                <when test="name(.) = 'commentBlock'">
                                    <fo:block-container height="45mm" space-before="5mm" padding = '1mm'
                                                        border-style="solid" border-width="thin" border-color="grey">
                                        <fo:block font-weight="bold" text-align="left" >
                                            <xsl:attribute name="font-size">
                                                <xsl:value-of select="$fontSize"/>
                                            </xsl:attribute>
                                            <xsl:text>Additional Comments:</xsl:text>
                                        </fo:block>
                                        <fo:block space-after="0.60mm"/>
                                    </fo:block-container>
                                </when>
                                <when test="name(.) = 'titleCenter'">
                                    <fo:block font-weight="bold" space-before="2.5mm" text-align="center">
                                        <xsl:attribute name="font-size">
                                            <xsl:value-of select="$titleFontSize"/>
                                        </xsl:attribute>
                                        <value-of select="titleText"/>
                                    </fo:block>
                                    <fo:block space-after="0.60mm"/>
                                </when>
                                <when test="name(.) = 'titleLeft'">
                                    <fo:block font-weight="bold" space-before="2.5mm" text-align="left">
                                        <xsl:attribute name="font-size">
                                            <xsl:value-of select="$titleFontSize"/>
                                        </xsl:attribute>
                                        <value-of select="titleText"/>
                                    </fo:block>
                                    <fo:block space-after="0.60mm"/>
                                </when>
                                <when test="name(.) = 'titleRight'">
                                    <fo:block font-weight="bold" space-before="2.5mm" text-align="right">
                                        <xsl:attribute name="font-size">
                                            <xsl:value-of select="$titleFontSize"/>
                                        </xsl:attribute>
                                        <value-of select="titleText"/>
                                    </fo:block>
                                    <fo:block space-after="0.60mm"/>
                                </when>
                                <when test="name(.) = 'image'">
                                    <fo:block font-weight="bold" space-before="5mm">
                                        <xsl:attribute name="font-size">
                                            <xsl:value-of select="$headerFontSize"/>
                                        </xsl:attribute>
                                        <value-of select="header"/>
                                    </fo:block>
                                    <fo:block border-style="solid" border-width="thin" border-color="grey">
                                        <fo:external-graphic src="{data}">
                                            <xsl:for-each select="./attributes/*">
                                                <xsl:variable name="attr_name">
                                                    <xsl:value-of select="name(.)"/>
                                                </xsl:variable>
                                                <attribute name="{$attr_name}">
                                                    <value-of select="."/>
                                                </attribute>
                                            </xsl:for-each>
                                        </fo:external-graphic>
                                    </fo:block>
                                    <for-each select="footer">
                                        <fo:block font-size="8.5pt" space-before="0.2mm">
                                            <value-of select="."/>
                                        </fo:block>
                                    </for-each>
                                    <fo:block space-after="0.60mm"/>
                                </when>
                                <when test="name(.) = 'map'">
                                    <fo:block font-weight="bold" space-before="5mm">
                                        <xsl:attribute name="font-size">
                                            <xsl:value-of select="$headerFontSize"/>
                                        </xsl:attribute>
                                        <value-of select="header"/>
                                    </fo:block>
                                    <fo:block>
                                        <fo:external-graphic src="{data}">
                                            <xsl:for-each select="./attributes/*">
                                                <xsl:variable name="attr_name">
                                                    <xsl:value-of select="name(.)"/>
                                                </xsl:variable>
                                                <attribute name="{$attr_name}">
                                                    <value-of select="."/>
                                                </attribute>
                                            </xsl:for-each>
                                        </fo:external-graphic>
                                    </fo:block>
                                    <for-each select="footer">
                                        <fo:block font-size="8.5pt" space-before="0.2mm">
                                            <value-of select="."/>
                                        </fo:block>
                                    </for-each>
                                    <fo:block space-after="0.60mm"/>
                                </when>
                                <when test="name(.) = 'table'">
                                    <fo:block font-weight="bold" space-before="5mm">
                                        <xsl:attribute name="font-size">
                                            <xsl:value-of select="$headerFontSize"/>
                                        </xsl:attribute>
                                        <value-of select="header"/>
                                    </fo:block>
                                    <fo:table table-layout="fixed" width="100%" text-align="left"
                                              display-align="center" border-color="grey"
                                              border-width="0.2mm"
                                              border-style="solid">
                                        <for-each select="width">
                                            <xsl:attribute name="width">
                                                <xsl:value-of select="."/>
                                            </xsl:attribute>
                                        </for-each>
                                        <xsl:attribute name="font-size">
                                            <xsl:value-of select="$fontSize"/>
                                        </xsl:attribute>
                                        <xsl:if test="@id='segmentationDetails'">
                                            <xsl:attribute name="text-align">center</xsl:attribute>
                                        </xsl:if>
                                        <xsl:if test="@id='segmentationGeometry'">
                                            <xsl:attribute name="text-align">center</xsl:attribute>
                                            <fo:table-column column-width="proportional-column-width(3.5)"/>
                                            <fo:table-column column-width="proportional-column-width(10)"/>
                                            <fo:table-column column-width="proportional-column-width(10)"/>
                                            <fo:table-column column-width="proportional-column-width(10)"/>
                                            <fo:table-column column-width="proportional-column-width(10)"/>
                                            <fo:table-column column-width="proportional-column-width(10)"/>
                                            <fo:table-column column-width="proportional-column-width(46.5)"/>
                                        </xsl:if>
                                        <xsl:if test="@id='segmentationDemand'">
                                            <xsl:attribute name="text-align">center</xsl:attribute>
                                            <fo:table-column column-width="proportional-column-width(3.5)"/>
                                            <fo:table-column column-width="proportional-column-width(10)"/>
                                            <fo:table-column column-width="proportional-column-width(10)"/>
                                            <fo:table-column column-width="proportional-column-width(10)"/>
                                            <fo:table-column column-width="proportional-column-width(15)"/>
                                            <fo:table-column column-width="proportional-column-width(15)"/>
                                            <fo:table-column column-width="proportional-column-width(18.25)"/>
                                            <fo:table-column column-width="proportional-column-width(18.25)"/>
                                        </xsl:if>
                                        <xsl:if test="@id='projectInformation'">
                                            <fo:table-column column-width="proportional-column-width(25)"/>
                                            <fo:table-column column-width="proportional-column-width(75)"/>
                                        </xsl:if>
                                        <xsl:if test="data/rowHeader">
                                            <fo:table-header>
                                                <for-each select="data/rowHeader">
                                                    <fo:table-row>
                                                        <for-each select="cell">
                                                            <fo:table-cell border-color="grey" border-width="0.2mm"
                                                                           border-style="solid" padding="0.25%" font-weight="bold">
                                                                <fo:block-container height="8mm">
                                                                    <fo:block>
                                                                        <value-of select="data"/>
                                                                    </fo:block>
                                                                </fo:block-container>
                                                            </fo:table-cell>
                                                        </for-each>
                                                    </fo:table-row>
                                                </for-each>
                                            </fo:table-header>
                                        </xsl:if>
                                        <fo:table-body>
                                            <for-each select="data/row">
                                                <fo:table-row>
                                                    <for-each select="columnHeader">
                                                        <fo:table-cell background-color="grey" border-color="grey"
                                                                       border-width="0.2mm"
                                                                       border-style="solid" padding="0.25%">
                                                            <fo:block>
                                                                <value-of select="data"/>
                                                            </fo:block>
                                                        </fo:table-cell>
                                                    </for-each>
                                                    <for-each select="cell">
                                                        <if test="contour">
                                                            <fo:table-cell border-color="grey" border-width="0.2mm"
                                                                           border-style="solid"
                                                                           background-color="{contour}"
                                                                           padding="0.25%">
                                                                <fo:block>
                                                                    <value-of select="data"/>
                                                                </fo:block>
                                                            </fo:table-cell>
                                                        </if>
                                                        <if test="not(contour)">
                                                            <fo:table-cell border-color="grey" border-width="0.2mm"
                                                                           border-style="solid" padding="0.05%">
                                                                <fo:block>
                                                                    <value-of select="data"/>
                                                                </fo:block>
                                                            </fo:table-cell>
                                                        </if>
                                                    </for-each>
                                                </fo:table-row>
                                            </for-each>
                                        </fo:table-body>
                                    </fo:table>
                                    <for-each select="footer">
                                        <fo:block font-size="8.5pt" space-before="0.2mm">
                                            <value-of select="."/>
                                        </fo:block>
                                    </for-each>
                                    <fo:block space-after="0.60mm"/>
                                </when>
                                <when test="name(.) = 'customTable'">
                                    <fo:block font-weight="bold" space-before="5mm">
                                        <xsl:attribute name="font-size">
                                            <xsl:value-of select="$headerFontSize"/>
                                        </xsl:attribute>
                                        <value-of select="header"/>
                                    </fo:block>
                                    <fo:table table-layout="fixed" width="100%" text-align="left"
                                              display-align="center" border-color="grey"
                                              border-width="0.2mm"
                                              border-style="solid">
                                        <xsl:attribute name="font-size">
                                            <xsl:value-of select="$fontSize"/>
                                        </xsl:attribute>
                                        <xsl:attribute name="text-align">center</xsl:attribute>
                                        <for-each select="data/columnSize/size">
                                            <xsl:element name="fo:table-column">
                                                <xsl:attribute name="column-width">
                                                    <xsl:variable name="col-size">
                                                        proportional-column-width(<xsl:value-of select="."/>)
                                                    </xsl:variable>
                                                    <xsl:value-of select="$col-size"/>
                                                </xsl:attribute>
                                            </xsl:element>
                                        </for-each>
                                        <xsl:if test="data/rowHeader">
                                            <fo:table-header>
                                                <for-each select="data/rowHeader">
                                                    <fo:table-row>
                                                        <for-each select="cell">
                                                            <fo:table-cell border-color="grey" border-width="0.2mm"
                                                                           border-style="solid" padding="0.25%" font-weight="bold">
                                                                <fo:block-container height="8mm">
                                                                    <fo:block>
                                                                        <value-of select="data"/>
                                                                    </fo:block>
                                                                </fo:block-container>
                                                            </fo:table-cell>
                                                        </for-each>
                                                    </fo:table-row>
                                                </for-each>
                                            </fo:table-header>
                                        </xsl:if>
                                        <fo:table-body>
                                            <for-each select="data/row">
                                                <fo:table-row>
                                                    <for-each select="columnHeader">
                                                        <fo:table-cell background-color="grey" border-color="grey"
                                                                       border-width="0.2mm"
                                                                       border-style="solid" padding="0.25%">
                                                            <fo:block>
                                                                <value-of select="data"/>
                                                            </fo:block>
                                                        </fo:table-cell>
                                                    </for-each>
                                                    <for-each select="cell">
                                                        <if test="contour">
                                                            <fo:table-cell border-color="grey" border-width="0.2mm"
                                                                           border-style="solid"
                                                                           background-color="{contour}"
                                                                           padding="0.05%">
                                                                <fo:block>
                                                                    <value-of select="data"/>
                                                                </fo:block>
                                                            </fo:table-cell>
                                                        </if>
                                                        <if test="not(contour)">
                                                            <fo:table-cell border-color="grey" border-width="0.2mm"
                                                                           border-style="solid" padding="0.05%">
                                                                <fo:block>
                                                                    <value-of select="data"/>
                                                                </fo:block>
                                                            </fo:table-cell>
                                                        </if>
                                                    </for-each>
                                                </fo:table-row>
                                            </for-each>
                                        </fo:table-body>
                                    </fo:table>
                                    <for-each select="footer">
                                        <fo:block font-size="8.5pt" space-before="0.2mm">
                                            <value-of select="."/>
                                        </fo:block>
                                    </for-each>
                                    <fo:block space-after="0.60mm"/>
                                </when>
                                <when test="name(.) = 'contour'">
                                    <fo:block font-weight="bold" page-break-before="always"
                                              space-before="5mm">
                                        <xsl:attribute name="font-size">
                                            <xsl:value-of select="$headerFontSize"/>
                                        </xsl:attribute>
                                        <value-of select="header"/>
                                    </fo:block>
                                    <fo:block-container>
                                        <fo:block>
                                            <fo:table table-layout="fixed" width="100%" border-color="grey"
                                                      border-width="0.4mm" border-style="solid" display-align="center"
                                                      text-align="center">
                                                <for-each select="data/columnSize/size">
                                                    <xsl:element name="fo:table-column">
                                                        <xsl:attribute name="column-width">
                                                            <xsl:variable name="col-size">
                                                                proportional-column-width(<xsl:value-of select="."/>)
                                                            </xsl:variable>
                                                            <xsl:value-of select="$col-size"/>
                                                        </xsl:attribute>
                                                    </xsl:element>
                                                </for-each>
                                                <xsl:if test="data/rowHeader">
                                                    <fo:table-header>
                                                        <for-each select="data/rowHeader">
                                                            <fo:table-row>
                                                                <for-each select="cell">
                                                                    <fo:table-cell border-color="grey"
                                                                                   border-width="0.2mm"
                                                                                   border-style="solid"
                                                                                   padding="0.25%">
                                                                        <xsl:if test="colSpan">
                                                                            <xsl:attribute
                                                                                    name="number-columns-spanned">
                                                                                <xsl:value-of select="colSpan"/>
                                                                            </xsl:attribute>
                                                                        </xsl:if>
                                                                        <fo:block-container height="8mm"
                                                                                            font-size="70%">
                                                                            <fo:block>
                                                                                <value-of select="data"/>
                                                                            </fo:block>
                                                                        </fo:block-container>
                                                                    </fo:table-cell>
                                                                </for-each>
                                                            </fo:table-row>
                                                        </for-each>
                                                    </fo:table-header>
                                                </xsl:if>
                                                <fo:table-body>
                                                    <for-each select="data/row">
                                                        <fo:table-row>
                                                            <for-each select="columnHeader">
                                                                <fo:table-cell border-color="grey" border-width="0.2mm"
                                                                               border-style="solid" padding="0.25%">

                                                                    <xsl:variable name="rowSize">
                                                                        <xsl:value-of select="../../rowSize/data"/>
                                                                    </xsl:variable>
                                                                    <fo:block-container height="{../../rowSize/data}mm"
                                                                                        column-width="100mm">
                                                                        <xsl:if test="$rowSize &lt; 3">
                                                                            <xsl:attribute name="font-size">
                                                                                40%
                                                                            </xsl:attribute>
                                                                        </xsl:if>
                                                                        <xsl:if test="$rowSize>=3">
                                                                            <xsl:attribute name="font-size">
                                                                                8.5pt
                                                                            </xsl:attribute>
                                                                        </xsl:if>
                                                                        <fo:block>
                                                                            <value-of select="data"/>
                                                                        </fo:block>
                                                                    </fo:block-container>
                                                                </fo:table-cell>
                                                            </for-each>
                                                            <for-each select="cell">
                                                                <fo:table-cell background-color="{data}"
                                                                               border-color="grey" border-width="0.2mm"
                                                                               border-style="solid" padding="0.05%">
                                                                    <fo:block-container height="{../../rowSize/data}mm">
                                                                        <fo:block>
                                                                        </fo:block>
                                                                    </fo:block-container>
                                                                </fo:table-cell>
                                                            </for-each>
                                                        </fo:table-row>
                                                    </for-each>
                                                </fo:table-body>


                                            </fo:table>
                                        </fo:block>
                                    </fo:block-container>
                                    <for-each select="footer">
                                        <fo:block font-size="8.5pt" space-before="0.2mm">
                                            <value-of select="."/>
                                        </fo:block>
                                    </for-each>
                                    <fo:block space-after="0.60mm"/>
                                </when>
                                <when test="name(.) = 'page-break'">
                                    <fo:block page-break-after="always"/>
                                </when>
                            </choose>
                        </for-each>
                    </fo:block>
                    <fo:block id="end-of-document" display-align="center">
                    </fo:block>
                </fo:flow>


            </fo:page-sequence>
        </fo:root>
    </template>

</stylesheet>