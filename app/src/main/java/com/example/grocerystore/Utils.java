package com.example.grocerystore;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.grocerystore.Models.CartItem;
import com.example.grocerystore.Models.GroceryItem;
import com.example.grocerystore.Models.Review;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Utils {

	private static final String DB_NAME = "fake_database";
	private static final String ALL_ITEMS_KEY = "all_items";
	private static final String CART_ITEMS_KEY = "cart_items";
	private static int ID = 0;
	private static int ORDER_ID = 0;
	private static Gson gson = new Gson();
	private static Type groceryListType = new TypeToken<ArrayList<GroceryItem>>() {
	}.getType();
	private static Type cartItemsListType = new TypeToken<ArrayList<CartItem>>() {
	}.getType();


	public static void initSharedPreferences(Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
		ArrayList<GroceryItem> allItems = gson.fromJson(sharedPreferences.getString(ALL_ITEMS_KEY, null), groceryListType);
		ArrayList<CartItem> cartItems = gson.fromJson(sharedPreferences.getString(CART_ITEMS_KEY, null), cartItemsListType);

		if (null == allItems) {
			initAllItems(context);
		}

		if (null == cartItems) {
			SharedPreferences.Editor editor = sharedPreferences.edit();
			cartItems = new ArrayList<>();
			editor.putString(CART_ITEMS_KEY, gson.toJson(cartItems));
			editor.commit();
		}
	}

	private static void initAllItems(Context context) {
		ArrayList<GroceryItem> allItems = new ArrayList<>();
		allItems.add(new GroceryItem("Milk",
				"Milk is a white, nutrient-rich liquid food produced in the mammary glands of mammals. It is the primary source of nutrition for infant mammals before they are able to digest other types of food.",
				"https://www.frieslandcampinainstitute.com/uploads/sites/4/2017/07/what-is-the-difference-between-full-fat-and-skimmed-milk-3.jpg",
				"drink", 3.99, 150
		));

		GroceryItem cocaCola = new GroceryItem("Coca cola",
				"Coca-Cola, or Coke, is a carbonated soft drink manufactured by The Coca-Cola Company. Originally marketed as a temperance drink and intended as a patent medicine, it was invented in the late 19th century by John Stith Pemberton",
				"https://m.vecernji.hr/media/img/ed/42/1fd6c7e076f936b7a4cc.jpeg",
				"drink", 1.99, 50);
		cocaCola.setPopularityPoints(4);
		cocaCola.setUserPoints(14);
		allItems.add(cocaCola);

		GroceryItem iceCream = new GroceryItem("Ice cream",
				"Ice cream is a sweetened frozen food typically eaten as a snack or dessert. It may be made from dairy milk or cream and is flavoured with a sweetener, either sugar or an alternative, and any spice, such as cocoa or vanilla.",
				"https://bitzngiggles.com/wp-content/uploads/2020/02/Rainbow-Ice-Cream-14-copy.jpg",
				"food", 9.99, 250);
		iceCream.setPopularityPoints(10);
		iceCream.setUserPoints(7);
		allItems.add(iceCream);


		allItems.add(new GroceryItem("Pepsi",
				"Pepsi is a carbonated soft drink manufactured by PepsiCo. Originally created and developed in 1893 by Caleb Bradham and introduced as Brad's Drink, it was renamed as Pepsi-Cola in 1898, and then shortened to Pepsi in 1961.",
				"https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcT2ENZ3B41dH0UuHg1hI_6JIdVEokUisbKTn5GFYhqqM1YH2NnN&usqp=CAU",
				"drink", 1.98, 150
		));

		SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString(ALL_ITEMS_KEY, gson.toJson(allItems));
		editor.commit();
	}

	public static int getID() {
		return ++ID;
	}

	public static int getOrderID() {
		return ++ORDER_ID;
	}

	public static ArrayList<GroceryItem> getAllItems(Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
		return gson.fromJson(sharedPreferences.getString(ALL_ITEMS_KEY, null), groceryListType);
	}

	public static GroceryItem getGroceryItemById(Context context, int id) {
		ArrayList<GroceryItem> allItems = getAllItems(context);
		if (allItems != null) {
			for (GroceryItem groceryItem : allItems) {
				if (groceryItem.getId() == id) {
					return groceryItem;
				}
			}
		}
		return null;
	}

	public static void addReview(Context context, Review review) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		ArrayList<GroceryItem> allItems = getAllItems(context);

		if (null != allItems) {
			for (GroceryItem groceryItem : allItems) {
				if (groceryItem.getId() == review.getGroceryItemId()) {
					ArrayList<Review> reviews = groceryItem.getReviews();
					reviews.add(review);
					groceryItem.setReviews(reviews);
				}
			}
			editor.remove(ALL_ITEMS_KEY);
			editor.putString(ALL_ITEMS_KEY, gson.toJson(allItems));
			editor.commit();
		}
	}

	public static ArrayList<Review> getReviewsByGroceryItemId(Context context, int id) {
		ArrayList<GroceryItem> allItems = getAllItems(context);
		if (null != allItems) {
			for (GroceryItem groceryItem : allItems) {
				if (groceryItem.getId() == id) {
					return groceryItem.getReviews();
				}
			}
		}
		return null;
	}

	public static ArrayList<String> getAllCategories(Context context) {
		ArrayList<GroceryItem> allItems = getAllItems(context);
		if (null != allItems) {
			ArrayList<String> categories = new ArrayList<>();
			for (GroceryItem item : allItems) {
				if (!categories.contains(item.getCategory().toLowerCase())) {
					categories.add(item.getCategory().toLowerCase());
				}
			}
			ArrayList<String> categoriesCapitalized = new ArrayList<>();
			for (String str : categories) {
				String s = str.substring(0, 1).toUpperCase() + str.substring(1);
				categoriesCapitalized.add(s);
			}
			return categoriesCapitalized;
		}
		return null;
	}

	public static ArrayList<CartItem> getCartItems(Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
		return gson.fromJson(sharedPreferences.getString(CART_ITEMS_KEY, null), cartItemsListType);
	}

	public static void addCartItem(Context context, CartItem cartItem) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		ArrayList<CartItem> cartItems = getCartItems(context);
		ArrayList<CartItem> addedCartItems = new ArrayList<>();

		if (null != cartItems) {
			boolean isInCart = false;
			for (CartItem item : cartItems) {
				if (item.getItem().getId() == cartItem.getItem().getId()) {
					isInCart = true;
				}
			}
			if (isInCart) {
				for (CartItem item : cartItems) {
					if (item.getItem().getId() == cartItem.getItem().getId()) {
						int amount = item.getAmount() + cartItem.getAmount();
						addedCartItems.add(new CartItem(cartItem.getItem(), amount));
					} else {
						addedCartItems.add(item);
					}
				}
			} else {
				addedCartItems.addAll(cartItems);
				addedCartItems.add(cartItem);
			}

			updateGroceryItemAmount(context, cartItem.getItem(), cartItem.getAmount());
		}

		editor.remove(CART_ITEMS_KEY);
		editor.putString(CART_ITEMS_KEY, gson.toJson(addedCartItems));
		editor.commit();
	}

	public static void removeCartItem(Context context, CartItem cartItem) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		ArrayList<CartItem> cartItems = getCartItems(context);
		ArrayList<CartItem> addedCartItems = new ArrayList<>();

		if (null != cartItems) {
			for (CartItem item : cartItems) {
				if (item.getItem().getId() != cartItem.getItem().getId()) {
					addedCartItems.add(item);
				}
			}

			updateGroceryItemAmount(context, cartItem.getItem(), -cartItem.getAmount());
		}

		editor.remove(CART_ITEMS_KEY);
		editor.putString(CART_ITEMS_KEY, gson.toJson(addedCartItems));
		editor.commit();
	}

	public static void clearCartItems(Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.remove(CART_ITEMS_KEY);
		editor.commit();
	}

	public static void updatePopularityPoints(Context context, GroceryItem item, int points) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		ArrayList<GroceryItem> allItems = getAllItems(context);
		ArrayList<GroceryItem> mewItems = new ArrayList<>();
		if (null != allItems) {
			for (GroceryItem i : allItems) {
				if (i.getId() == item.getId()) {
					i.setPopularityPoints(i.getPopularityPoints() + points);
				}
				mewItems.add(i);
			}
		}

		editor.remove(ALL_ITEMS_KEY);
		editor.putString(ALL_ITEMS_KEY, gson.toJson(mewItems));
		editor.commit();
	}

	public static void updateUserPoints(Context context, GroceryItem item, int points) {
		/*
		 * Update user point based on next criteria:
		 * User visits item:         1 point
		 * User rate item:           (Rate*2) points
		 * User review item:         3 points
		 * User purchase item:       4 points
		 * Item appear in search:    1 point
		 * User read item            1 point every minute
		 */
		SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		ArrayList<GroceryItem> allItems = getAllItems(context);
		ArrayList<GroceryItem> mewItems = new ArrayList<>();
		if (null != allItems) {
			for (GroceryItem i : allItems) {
				if (i.getId() == item.getId()) {
					i.setUserPoints(i.getUserPoints() + points);
				}
				mewItems.add(i);
			}
		}

		editor.remove(ALL_ITEMS_KEY);
		editor.putString(ALL_ITEMS_KEY, gson.toJson(mewItems));
		editor.commit();
	}

	public static String getLicences() {
		String licence = "";
		licence += "GLIDE\n";
		licence += "License for everything not in third_party and not otherwise marked:\n" +
				"\n" +
				"Copyright 2014 Google, Inc. All rights reserved.\n" +
				"\n" +
				"Redistribution and use in source and binary forms, with or without modification, are\n" +
				"permitted provided that the following conditions are met:\n" +
				"\n" +
				"   1. Redistributions of source code must retain the above copyright notice, this list of\n" +
				"         conditions and the following disclaimer.\n" +
				"\n" +
				"   2. Redistributions in binary form must reproduce the above copyright notice, this list\n" +
				"         of conditions and the following disclaimer in the documentation and/or other materials\n" +
				"         provided with the distribution.\n" +
				"\n" +
				"THIS SOFTWARE IS PROVIDED BY GOOGLE, INC. ``AS IS'' AND ANY EXPRESS OR IMPLIED\n" +
				"WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND\n" +
				"FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL GOOGLE, INC. OR\n" +
				"CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR\n" +
				"CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR\n" +
				"SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON\n" +
				"ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING\n" +
				"NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF\n" +
				"ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.\n" +
				"\n" +
				"The views and conclusions contained in the software and documentation are those of the\n" +
				"authors and should not be interpreted as representing official policies, either expressed\n" +
				"or implied, of Google, Inc.\n" +
				"---------------------------------------------------------------------------------------------\n" +
				"License for third_party/disklrucache:\n" +
				"\n" +
				"Copyright 2012 Jake Wharton\n" +
				"Copyright 2011 The Android Open Source Project\n" +
				"\n" +
				"Licensed under the Apache License, Version 2.0 (the \"License\");\n" +
				"you may not use this file except in compliance with the License.\n" +
				"You may obtain a copy of the License at\n" +
				"\n" +
				"   http://www.apache.org/licenses/LICENSE-2.0\n" +
				"\n" +
				"Unless required by applicable law or agreed to in writing, software\n" +
				"distributed under the License is distributed on an \"AS IS\" BASIS,\n" +
				"WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n" +
				"See the License for the specific language governing permissions and\n" +
				"limitations under the License.\n" +
				"---------------------------------------------------------------------------------------------\n" +
				"License for third_party/gif_decoder:\n" +
				"\n" +
				"Copyright (c) 2013 Xcellent Creations, Inc.\n" +
				"\n" +
				"Permission is hereby granted, free of charge, to any person obtaining\n" +
				"a copy of this software and associated documentation files (the\n" +
				"\"Software\"), to deal in the Software without restriction, including\n" +
				"without limitation the rights to use, copy, modify, merge, publish,\n" +
				"distribute, sublicense, and/or sell copies of the Software, and to\n" +
				"permit persons to whom the Software is furnished to do so, subject to\n" +
				"the following conditions:\n" +
				"\n" +
				"The above copyright notice and this permission notice shall be\n" +
				"included in all copies or substantial portions of the Software.\n" +
				"\n" +
				"THE SOFTWARE IS PROVIDED \"AS IS\", WITHOUT WARRANTY OF ANY KIND,\n" +
				"EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF\n" +
				"MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND\n" +
				"NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE\n" +
				"LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION\n" +
				"OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION\n" +
				"WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.\n" +
				"---------------------------------------------------------------------------------------------\n" +
				"License for third_party/gif_encoder/AnimatedGifEncoder.java and\n" +
				"third_party/gif_encoder/LZWEncoder.java:\n" +
				"\n" +
				"No copyright asserted on the source code of this class. May be used for any\n" +
				"purpose, however, refer to the Unisys LZW patent for restrictions on use of\n" +
				"the associated LZWEncoder class. Please forward any corrections to\n" +
				"kweiner@fmsware.com.\n" +
				"\n" +
				"-----------------------------------------------------------------------------\n" +
				"License for third_party/gif_encoder/NeuQuant.java\n" +
				"\n" +
				"Copyright (c) 1994 Anthony Dekker\n" +
				"\n" +
				"NEUQUANT Neural-Net quantization algorithm by Anthony Dekker, 1994. See\n" +
				"\"Kohonen neural networks for optimal colour quantization\" in \"Network:\n" +
				"Computation in Neural Systems\" Vol. 5 (1994) pp 351-367. for a discussion of\n" +
				"the algorithm.\n" +
				"\n" +
				"Any party obtaining a copy of these files from the author, directly or\n" +
				"indirectly, is granted, free of charge, a full and unrestricted irrevocable,\n" +
				"world-wide, paid up, royalty-free, nonexclusive right and license to deal in\n" +
				"this software and documentation files (the \"Software\"), including without\n" +
				"limitation the rights to use, copy, modify, merge, publish, distribute,\n" +
				"sublicense, and/or sell copies of the Software, and to permit persons who\n" +
				"receive copies from any such party to do so, with the only requirement being\n" +
				"that this copyright notice remain intact.";
		licence += "\n\n";
		licence += "RETROFIT\n";
		licence += "Copyright 2013 Square, Inc.\n" +
				"\n" +
				"Licensed under the Apache License, Version 2.0 (the \"License\");\n" +
				"you may not use this file except in compliance with the License.\n" +
				"You may obtain a copy of the License at\n" +
				"\n" +
				"   http://www.apache.org/licenses/LICENSE-2.0\n" +
				"\n" +
				"Unless required by applicable law or agreed to in writing, software\n" +
				"distributed under the License is distributed on an \"AS IS\" BASIS,\n" +
				"WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n" +
				"See the License for the specific language governing permissions and\n" +
				"limitations under the License.";
		licence += "\n\n";
		licence += "GSON, MATERIAL DESIGN, OKHTTP\n";
		licence += "  Apache License\n" +
				"                           Version 2.0, January 2004\n" +
				"                        http://www.apache.org/licenses/\n" +
				"\n" +
				"   TERMS AND CONDITIONS FOR USE, REPRODUCTION, AND DISTRIBUTION\n" +
				"\n" +
				"   1. Definitions.\n" +
				"\n" +
				"      \"License\" shall mean the terms and conditions for use, reproduction,\n" +
				"      and distribution as defined by Sections 1 through 9 of this document.\n" +
				"\n" +
				"      \"Licensor\" shall mean the copyright owner or entity authorized by\n" +
				"      the copyright owner that is granting the License.\n" +
				"\n" +
				"      \"Legal Entity\" shall mean the union of the acting entity and all\n" +
				"      other entities that control, are controlled by, or are under common\n" +
				"      control with that entity. For the purposes of this definition,\n" +
				"      \"control\" means (i) the power, direct or indirect, to cause the\n" +
				"      direction or management of such entity, whether by contract or\n" +
				"      otherwise, or (ii) ownership of fifty percent (50%) or more of the\n" +
				"      outstanding shares, or (iii) beneficial ownership of such entity.\n" +
				"\n" +
				"      \"You\" (or \"Your\") shall mean an individual or Legal Entity\n" +
				"      exercising permissions granted by this License.\n" +
				"\n" +
				"      \"Source\" form shall mean the preferred form for making modifications,\n" +
				"      including but not limited to software source code, documentation\n" +
				"      source, and configuration files.\n" +
				"\n" +
				"      \"Object\" form shall mean any form resulting from mechanical\n" +
				"      transformation or translation of a Source form, including but\n" +
				"      not limited to compiled object code, generated documentation,\n" +
				"      and conversions to other media types.\n" +
				"\n" +
				"      \"Work\" shall mean the work of authorship, whether in Source or\n" +
				"      Object form, made available under the License, as indicated by a\n" +
				"      copyright notice that is included in or attached to the work\n" +
				"      (an example is provided in the Appendix below).\n" +
				"\n" +
				"      \"Derivative Works\" shall mean any work, whether in Source or Object\n" +
				"      form, that is based on (or derived from) the Work and for which the\n" +
				"      editorial revisions, annotations, elaborations, or other modifications\n" +
				"      represent, as a whole, an original work of authorship. For the purposes\n" +
				"      of this License, Derivative Works shall not include works that remain\n" +
				"      separable from, or merely link (or bind by name) to the interfaces of,\n" +
				"      the Work and Derivative Works thereof.\n" +
				"\n" +
				"      \"Contribution\" shall mean any work of authorship, including\n" +
				"      the original version of the Work and any modifications or additions\n" +
				"      to that Work or Derivative Works thereof, that is intentionally\n" +
				"      submitted to Licensor for inclusion in the Work by the copyright owner\n" +
				"      or by an individual or Legal Entity authorized to submit on behalf of\n" +
				"      the copyright owner. For the purposes of this definition, \"submitted\"\n" +
				"      means any form of electronic, verbal, or written communication sent\n" +
				"      to the Licensor or its representatives, including but not limited to\n" +
				"      communication on electronic mailing lists, source code control systems,\n" +
				"      and issue tracking systems that are managed by, or on behalf of, the\n" +
				"      Licensor for the purpose of discussing and improving the Work, but\n" +
				"      excluding communication that is conspicuously marked or otherwise\n" +
				"      designated in writing by the copyright owner as \"Not a Contribution.\"\n" +
				"\n" +
				"      \"Contributor\" shall mean Licensor and any individual or Legal Entity\n" +
				"      on behalf of whom a Contribution has been received by Licensor and\n" +
				"      subsequently incorporated within the Work.\n" +
				"\n" +
				"   2. Grant of Copyright License. Subject to the terms and conditions of\n" +
				"      this License, each Contributor hereby grants to You a perpetual,\n" +
				"      worldwide, non-exclusive, no-charge, royalty-free, irrevocable\n" +
				"      copyright license to reproduce, prepare Derivative Works of,\n" +
				"      publicly display, publicly perform, sublicense, and distribute the\n" +
				"      Work and such Derivative Works in Source or Object form.\n" +
				"\n" +
				"   3. Grant of Patent License. Subject to the terms and conditions of\n" +
				"      this License, each Contributor hereby grants to You a perpetual,\n" +
				"      worldwide, non-exclusive, no-charge, royalty-free, irrevocable\n" +
				"      (except as stated in this section) patent license to make, have made,\n" +
				"      use, offer to sell, sell, import, and otherwise transfer the Work,\n" +
				"      where such license applies only to those patent claims licensable\n" +
				"      by such Contributor that are necessarily infringed by their\n" +
				"      Contribution(s) alone or by combination of their Contribution(s)\n" +
				"      with the Work to which such Contribution(s) was submitted. If You\n" +
				"      institute patent litigation against any entity (including a\n" +
				"      cross-claim or counterclaim in a lawsuit) alleging that the Work\n" +
				"      or a Contribution incorporated within the Work constitutes direct\n" +
				"      or contributory patent infringement, then any patent licenses\n" +
				"      granted to You under this License for that Work shall terminate\n" +
				"      as of the date such litigation is filed.\n" +
				"\n" +
				"   4. Redistribution. You may reproduce and distribute copies of the\n" +
				"      Work or Derivative Works thereof in any medium, with or without\n" +
				"      modifications, and in Source or Object form, provided that You\n" +
				"      meet the following conditions:\n" +
				"\n" +
				"      (a) You must give any other recipients of the Work or\n" +
				"          Derivative Works a copy of this License; and\n" +
				"\n" +
				"      (b) You must cause any modified files to carry prominent notices\n" +
				"          stating that You changed the files; and\n" +
				"\n" +
				"      (c) You must retain, in the Source form of any Derivative Works\n" +
				"          that You distribute, all copyright, patent, trademark, and\n" +
				"          attribution notices from the Source form of the Work,\n" +
				"          excluding those notices that do not pertain to any part of\n" +
				"          the Derivative Works; and\n" +
				"\n" +
				"      (d) If the Work includes a \"NOTICE\" text file as part of its\n" +
				"          distribution, then any Derivative Works that You distribute must\n" +
				"          include a readable copy of the attribution notices contained\n" +
				"          within such NOTICE file, excluding those notices that do not\n" +
				"          pertain to any part of the Derivative Works, in at least one\n" +
				"          of the following places: within a NOTICE text file distributed\n" +
				"          as part of the Derivative Works; within the Source form or\n" +
				"          documentation, if provided along with the Derivative Works; or,\n" +
				"          within a display generated by the Derivative Works, if and\n" +
				"          wherever such third-party notices normally appear. The contents\n" +
				"          of the NOTICE file are for informational purposes only and\n" +
				"          do not modify the License. You may add Your own attribution\n" +
				"          notices within Derivative Works that You distribute, alongside\n" +
				"          or as an addendum to the NOTICE text from the Work, provided\n" +
				"          that such additional attribution notices cannot be construed\n" +
				"          as modifying the License.\n" +
				"\n" +
				"      You may add Your own copyright statement to Your modifications and\n" +
				"      may provide additional or different license terms and conditions\n" +
				"      for use, reproduction, or distribution of Your modifications, or\n" +
				"      for any such Derivative Works as a whole, provided Your use,\n" +
				"      reproduction, and distribution of the Work otherwise complies with\n" +
				"      the conditions stated in this License.\n" +
				"\n" +
				"   5. Submission of Contributions. Unless You explicitly state otherwise,\n" +
				"      any Contribution intentionally submitted for inclusion in the Work\n" +
				"      by You to the Licensor shall be under the terms and conditions of\n" +
				"      this License, without any additional terms or conditions.\n" +
				"      Notwithstanding the above, nothing herein shall supersede or modify\n" +
				"      the terms of any separate license agreement you may have executed\n" +
				"      with Licensor regarding such Contributions.\n" +
				"\n" +
				"   6. Trademarks. This License does not grant permission to use the trade\n" +
				"      names, trademarks, service marks, or product names of the Licensor,\n" +
				"      except as required for reasonable and customary use in describing the\n" +
				"      origin of the Work and reproducing the content of the NOTICE file.\n" +
				"\n" +
				"   7. Disclaimer of Warranty. Unless required by applicable law or\n" +
				"      agreed to in writing, Licensor provides the Work (and each\n" +
				"      Contributor provides its Contributions) on an \"AS IS\" BASIS,\n" +
				"      WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or\n" +
				"      implied, including, without limitation, any warranties or conditions\n" +
				"      of TITLE, NON-INFRINGEMENT, MERCHANTABILITY, or FITNESS FOR A\n" +
				"      PARTICULAR PURPOSE. You are solely responsible for determining the\n" +
				"      appropriateness of using or redistributing the Work and assume any\n" +
				"      risks associated with Your exercise of permissions under this License.\n" +
				"\n" +
				"   8. Limitation of Liability. In no event and under no legal theory,\n" +
				"      whether in tort (including negligence), contract, or otherwise,\n" +
				"      unless required by applicable law (such as deliberate and grossly\n" +
				"      negligent acts) or agreed to in writing, shall any Contributor be\n" +
				"      liable to You for damages, including any direct, indirect, special,\n" +
				"      incidental, or consequential damages of any character arising as a\n" +
				"      result of this License or out of the use or inability to use the\n" +
				"      Work (including but not limited to damages for loss of goodwill,\n" +
				"      work stoppage, computer failure or malfunction, or any and all\n" +
				"      other commercial damages or losses), even if such Contributor\n" +
				"      has been advised of the possibility of such damages.\n" +
				"\n" +
				"   9. Accepting Warranty or Additional Liability. While redistributing\n" +
				"      the Work or Derivative Works thereof, You may choose to offer,\n" +
				"      and charge a fee for, acceptance of support, warranty, indemnity,\n" +
				"      or other liability obligations and/or rights consistent with this\n" +
				"      License. However, in accepting such obligations, You may act only\n" +
				"      on Your own behalf and on Your sole responsibility, not on behalf\n" +
				"      of any other Contributor, and only if You agree to indemnify,\n" +
				"      defend, and hold each Contributor harmless for any liability\n" +
				"      incurred by, or claims asserted against, such Contributor by reason\n" +
				"      of your accepting any such warranty or additional liability.\n" +
				"\n" +
				"   END OF TERMS AND CONDITIONS\n" +
				"\n" +
				"   APPENDIX: How to apply the Apache License to your work.\n" +
				"\n" +
				"      To apply the Apache License to your work, attach the following\n" +
				"      boilerplate notice, with the fields enclosed by brackets \"[]\"\n" +
				"      replaced with your own identifying information. (Don't include\n" +
				"      the brackets!)  The text should be enclosed in the appropriate\n" +
				"      comment syntax for the file format. We also recommend that a\n" +
				"      file or class name and description of purpose be included on the\n" +
				"      same \"printed page\" as the copyright notice for easier\n" +
				"      identification within third-party archives.\n" +
				"\n" +
				"   Copyright [yyyy] [name of copyright owner]\n" +
				"\n" +
				"   Licensed under the Apache License, Version 2.0 (the \"License\");\n" +
				"   you may not use this file except in compliance with the License.\n" +
				"   You may obtain a copy of the License at\n" +
				"\n" +
				"       http://www.apache.org/licenses/LICENSE-2.0\n" +
				"\n" +
				"   Unless required by applicable law or agreed to in writing, software\n" +
				"   distributed under the License is distributed on an \"AS IS\" BASIS,\n" +
				"   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n" +
				"   See the License for the specific language governing permissions and\n" +
				"   limitations under the License.";

		return licence;
	}

	private static void updateGroceryItemAmount(Context context, GroceryItem groceryItem, int amount) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		ArrayList<GroceryItem> groceryItems = getAllItems(context);
		ArrayList<GroceryItem> editedItems = new ArrayList<>();
		if (null != groceryItems) {
			for (GroceryItem item : groceryItems) {
				if (item.getId() == groceryItem.getId()) {
					int newAmount = item.getAvailableAmount() - amount;
					item.setAvailableAmount(newAmount);
				}
				editedItems.add(item);
			}
		}

		editor.remove(ALL_ITEMS_KEY);
		editor.putString(ALL_ITEMS_KEY, gson.toJson(editedItems));
		editor.commit();
	}
}
