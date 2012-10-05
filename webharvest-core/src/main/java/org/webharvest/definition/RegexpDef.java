/*  Copyright (c) 2006-2007, Vladimir Nikic
    All rights reserved.

    Redistribution and use of this software in source and binary forms,
    with or without modification, are permitted provided that the following
    conditions are met:

    * Redistributions of source code must retain the above
      copyright notice, this list of conditions and the
      following disclaimer.

    * Redistributions in binary form must reproduce the above
      copyright notice, this list of conditions and the
      following disclaimer in the documentation and/or other
      materials provided with the distribution.

    * The name of Web-Harvest may not be used to endorse or promote
      products derived from this software without specific prior
      written permission.

    THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
    AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
    IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
    ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
    LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
    CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
    SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
    INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
    CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
    ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
    POSSIBILITY OF SUCH DAMAGE.

    You can contact Vladimir Nikic by sending e-mail to
    nikic_vladimir@yahoo.com. Please include the word "Web-Harvest" in the
    subject line.
*/
package org.webharvest.definition;

import org.webharvest.runtime.processors.AbstractProcessor;

/**
 * Definition of regular expression processor.
 */
public class RegexpDef extends WebHarvestPluginDef {

    private DefinitionResolver definitionResolver = DefinitionResolver.INSTANCE;

    private String max;
    private String replace;

    private IElementDef regexpPatternDef;
    private IElementDef regexpSourceDef;
    private IElementDef regexpResultDef;
    private String flagCaseInsensitive;
    private String flagMultiline;
    private String flagDotall;
    private String flagUnicodecase;
    private String flagCanoneq;

    public RegexpDef(XmlNode xmlNode, Class<? extends AbstractProcessor> processorClass) {
        super(xmlNode, false, processorClass);

        this.max = xmlNode.getAttribute("max");
        this.replace = xmlNode.getAttribute("replace");
        this.flagCaseInsensitive = xmlNode.getAttribute("flag-caseinsensitive");
        this.flagMultiline = xmlNode.getAttribute("flag-multiline");
        this.flagDotall = xmlNode.getAttribute("flag-dotall");
        this.flagUnicodecase = xmlNode.getAttribute("flag-unicodecase");
        this.flagCanoneq = xmlNode.getAttribute("flag-canoneq");

        XmlNode regexpPatternDefNode = xmlNode.getFirstSubnode(new ElementName("regexp-pattern", xmlNode.getUri()));
        regexpPatternDef = regexpPatternDefNode == null ? null : new ElementDefProxy(regexpPatternDefNode);

        XmlNode regexpSourceDefNode = xmlNode.getFirstSubnode(new ElementName("regexp-source", xmlNode.getUri()));
        regexpSourceDef = regexpSourceDefNode == null ? null : new ElementDefProxy(regexpSourceDefNode);

        XmlNode regexpResultDefNode = xmlNode.getFirstSubnode(new ElementName("regexp-result", xmlNode.getUri()));
        regexpResultDef = regexpResultDefNode == null ? null : new ElementDefProxy(regexpResultDefNode);
    }

    public String getMax() {
        return max;
    }

    public String getReplace() {
        return replace;
    }

    public String getFlagCaseInsensitive() {
        return flagCaseInsensitive;
    }

    public String getFlagMultiline() {
        return flagMultiline;
    }

    public String getFlagDotall() {
        return flagDotall;
    }

    public String getFlagUnicodecase() {
        return flagUnicodecase;
    }

    public String getFlagCanoneq() {
        return flagCanoneq;
    }

    public IElementDef getRegexpPatternDef() {
        return regexpPatternDef;
    }

    public IElementDef getRegexpResultDef() {
        return regexpResultDef;
    }

    public IElementDef getRegexpSourceDef() {
        return regexpSourceDef;
    }

    public String getShortElementName() {
        return "regexp";
    }

    public IElementDef[] getOperationDefs() {
        IElementDef[] result = new IElementDef[regexpResultDef != null ? 3 : 2];
        result[0] = regexpPatternDef;
        result[1] = regexpSourceDef;
        if (regexpResultDef != null) {
            result[2] = regexpResultDef;
        }

        return result;
    }

}