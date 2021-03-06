package net.billylieurance.azuresearch;

/*
 Copyright 2012 William Lieurance

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class AzureSearchSpellingSuggestionQuery extends
		AbstractAzureSearchQuery<AzureSearchSpellingSuggestionResult> {

	// private static final Logger log = Logger
	// .getLogger(AzureSearchNewsQuery.class.getName());

	
	//https://api.datamarket.azure.com/Data.ashx/Bing/Search/v1/SpellingSuggestions?Query=%27Okahoma%20Sooners%27&$top=50&$format=Atom

	@Override
	public String getQueryPath() {
		return AZURESEARCH_PATH + querytypeToUrl(AZURESEARCH_QUERYTYPE.SPELLINGSUGGESTION);
	}
	


	@Override
	public AzureSearchSpellingSuggestionResult parseEntry(Node entry) {
		AzureSearchSpellingSuggestionResult returnable = new AzureSearchSpellingSuggestionResult();

		try {
			NodeList l1kids = entry.getChildNodes();

			for (int i = 0; i < l1kids.getLength(); i++) {
				Node l1kid = l1kids.item(i);
				if (l1kid.getNodeName().equals("content")) {
					/* parse <content>
				    <content type="application/xml">
				      <m:properties>
				        <d:ID m:type="Edm.Guid">06de675d-02f8-42ad-b9e4-cfe1062caaa9</d:ID>
				        <d:Title m:type="Edm.String">Oklahoma Sooners Wallpaper</d:Title>
				        <d:BingUrl m:type="Edm.String">http://www.bing.com/search?q=Oklahoma+Sooners+Wallpaper</d:BingUrl>
				      </m:properties>
				    </content>
					 */
					NodeList contentKids = l1kid.getFirstChild()
							.getChildNodes();

					for (int j = 0; j < contentKids.getLength(); j++) {
						Node contentKid = contentKids.item(j);

						try {
							if (contentKid.getNodeName().equals("d:ID")) {
								returnable.setId(contentKid.getTextContent());
							} else if (contentKid.getNodeName().equals(
									"d:Value")) {
								returnable
										.setValue(contentKid.getTextContent());
							}
						} catch (Exception ex) {
							// no one cares
							ex.printStackTrace();
						}
					}
				}

			}
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}

		return returnable;
	}

	@Override
	public String getAdditionalUrlQuery() {
		return "";
	}

}
