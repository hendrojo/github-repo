package roman.convert.util;

import java.util.Map.Entry;

public class Util {
	public static void initializeMap() {
		Constant.mapRomanSymbol.put("I",1);
		Constant.mapRomanSymbol.put("V",5);
		Constant.mapRomanSymbol.put("X",10);
		Constant.mapRomanSymbol.put("L",50);
		Constant.mapRomanSymbol.put("C",100);
		Constant.mapRomanSymbol.put("D",500);
		Constant.mapRomanSymbol.put("M",1000);
		
		Constant.mapAlienSymbol.put("I","");
		Constant.mapAlienSymbol.put("V","");
		Constant.mapAlienSymbol.put("X","");
		Constant.mapAlienSymbol.put("L","");
		Constant.mapAlienSymbol.put("C","");
		Constant.mapAlienSymbol.put("D","");
		Constant.mapAlienSymbol.put("M","");
		
		Constant.mapMetalCredits.put("IRON",0.0);
		Constant.mapMetalCredits.put("SILVER",0.0);
		Constant.mapMetalCredits.put("GOLD",0.0);
	}

	public static String convertNumSym(int iNumber) {
		if (iNumber<1 || iNumber>3999) {
			return "0";
		} else {
			String strNumber = String.valueOf(iNumber);
			String strSymbol = "";
			try {
				for (int i=0;i<strNumber.length();i++) {
					strSymbol+=symbolNum(strNumber.length()-i,Character.getNumericValue(strNumber.charAt(i)));
				}
			} catch (Exception ex) {
				strSymbol = "0";
			}
			return strSymbol;
		}
	}

	private static String getKeyFromMap(String strMapType,String strValue) {
		if ("Roman".equals(strMapType)) {
			for (Entry<String,Integer> entry : Constant.mapRomanSymbol.entrySet()) {
	            if (entry.getValue()==Integer.parseInt(strValue)) {
	                return entry.getKey();
	            }
	        }
		} else if ("Alien".equals(strMapType)) {
			for (Entry<String,String> entry : Constant.mapAlienSymbol.entrySet()) {
	            if (entry.getValue().equals(strValue)) {
	                return entry.getKey();
	            }
	        }
		} else if ("Credits".equals(strMapType)) {
			for (Entry<String,Double> entry : Constant.mapMetalCredits.entrySet()) {
	            if (entry.getValue()==Double.parseDouble(strValue)) {
	                return entry.getKey();
	            }
	        }
		}
		return "";
	}
	
	private static String symbolNum(int iPos,int iNum) {
		String strReturn = "";
		if (iPos==4) {
			for (int i=iNum;i>0;i--) {
				strReturn+=getKeyFromMap("Roman",String.valueOf(1000));
			}
		} else if (iPos<=3) {
			int iOne = 0;
			int iFive = 0;
			int iTen = 0;
			if (iPos==3) {
				iOne = 100;
				iFive = 500;
				iTen = 1000;
			} else if (iPos==2) {
				iOne = 10;
				iFive = 50;
				iTen = 100;
			} else if (iPos==1) {
				iOne = 1;
				iFive = 5;
				iTen = 10;
			}
			
			if (iNum>=1 && iNum<=3) {
				for (int i=iNum;i>0;i--) {
					strReturn+=getKeyFromMap("Roman",String.valueOf(iOne));
				}
			} else if (iNum==4) {
				strReturn+=getKeyFromMap("Roman",String.valueOf(iOne));
				strReturn+=getKeyFromMap("Roman",String.valueOf(iFive));
			} else if (iNum==5) {
				strReturn+=getKeyFromMap("Roman",String.valueOf(iFive));
			} else if (iNum>=6 && iNum<=8) {
				strReturn+=getKeyFromMap("Roman",String.valueOf(iFive));
				for (int i=iNum-5;i>0;i--) {
					strReturn+=getKeyFromMap("Roman",String.valueOf(iOne));
				} 
			} else if (iNum==9) {
				strReturn+=getKeyFromMap("Roman",String.valueOf(iOne));
				strReturn+=getKeyFromMap("Roman",String.valueOf(iTen));
			}
		}
		return strReturn;
	}
	
	public static int convertSymNum(String strSymbol) {
		int iNumber = 0;
		int iRetNum = 0;
		int iLastRet = 0;
		int iSameNum = 1;
		String strLast = "";
		String strChar = "";
		boolean blnBreak = false;
		try {
			for (int i=0;i<strSymbol.length();i++) {
				strChar = String.valueOf(strSymbol.charAt(i));
				if (!"".equals(strLast)) {
					if (strLast.equals(strChar)) {
						iSameNum++;
					} else {
						iSameNum = 1;
					}
					if ("I".equals(strChar) || "X".equals(strChar) || "C".equals(strChar) || "M".equals(strChar)) {
						if (iSameNum>3) {
							blnBreak = true;
						}
					}
					if ("V".equals(strChar) || "L".equals(strChar) || "D".equals(strChar)) {
						if (iSameNum>1) {
							blnBreak = true;
						}
					}
				}
				if ("I".equals(strChar) || "X".equals(strChar) || "C".equals(strChar)) {
					if (i+1<strSymbol.length()) {
						iRetNum = convNum(strSymbol,strChar,i);
						if (iRetNum!=0) {
							i++;
						} else {
							iRetNum = Constant.mapRomanSymbol.get(strChar);
						}
					} else {
						iRetNum = Constant.mapRomanSymbol.get(strChar);
					}
				} else {
					iRetNum = Constant.mapRomanSymbol.get(strChar);
				}
				if (iLastRet>0 && iLastRet<iRetNum) {
					blnBreak = true;
				} else {
					iLastRet = iRetNum;
				}
				if (blnBreak) {
					iNumber = 0;
					break;
				}
				iNumber+=iRetNum;
				strLast = String.valueOf(strSymbol.charAt(i));
			}
		} catch (Exception ex) {
			iNumber = 0;
		}
		
		return iNumber;
	}
	
	private static int convNum(String strSymbol,String strChar,int iPos) {
		int iNumber = 0;
		String strOne = "";
		String strFive = "";
		String strTen = ""; 
		if ("I".equals(strChar)) {
			strOne = "I";
			strFive = "V";
			strTen = "X";
		} else if ("X".equals(strChar)) {
			strOne = "X";
			strFive = "L";
			strTen = "C";
		} else if ("C".equals(strChar)) {
			strOne = "C";
			strFive = "D";
			strTen = "M";
		}
		if (String.valueOf(strSymbol.charAt(iPos+1)).equals(strFive) || String.valueOf(strSymbol.charAt(iPos+1)).equals(strTen)) {
			if (String.valueOf(strSymbol.charAt(iPos+1)).equals(strFive)) {
				iNumber+=Constant.mapRomanSymbol.get(strFive);
			} else if (String.valueOf(strSymbol.charAt(iPos+1)).equals(strTen)) {
				iNumber+=Constant.mapRomanSymbol.get(strTen);
			}
			iNumber-=Constant.mapRomanSymbol.get(strOne);
		}
		return iNumber;
	}
	
	public static String setAlienSymbol(String [] strData) {
		if (!"IS".equals(strData[1])) {
			return Constant.strNoResult + "\n";
		} else if (!Constant.mapRomanSymbol.containsKey(strData[2])) {
			return Constant.strNoResult + "\n";
		} else {
			Constant.mapAlienSymbol.put(strData[2],strData[0]);
			return "\n";
		}
	}
	
	public static String setMetalCredits(String [] strData) {
		String strRomanSym = "";
		String strMetal = "";
		double iMetalPrice = 0;
		try {
			for (int i=0;i<strData.length;i++) {
				if ("IS".equals(strData[i])) {
					if (i+2!=strData.length-1 || !"CREDITS".equals(strData[i+2])) {
						return Constant.strNoResult + "\n";
					} else {
						iMetalPrice = Integer.parseInt(strData[i+1]);
						break;
					}
				} else if (!"GOLD".equals(strData[i]) && !"SILVER".equals(strData[i]) && !"IRON".equals(strData[i])) {
					if ("".equals(getKeyFromMap("Alien",strData[i]))) {
						return Constant.strNoResult + "\n";
					} else {
						strRomanSym+=getKeyFromMap("Alien",strData[i]);
					}
				} else if ("GOLD".equals(strData[i]) || "SILVER".equals(strData[i]) || "IRON".equals(strData[i])) {
					if ("".equals(strMetal)) {
						strMetal = strData[i];
					} else {
						return Constant.strNoResult + "\n";
					}
				}
			}
		} catch (Exception ex) {
			return Constant.strNoResult + "\n";
		}
		if (iMetalPrice>0) {
			int iNumber = convertSymNum(strRomanSym);
			if (iNumber<=0) {
				return Constant.strNoResult + "\n";
			} else {
				iMetalPrice = iMetalPrice/iNumber;
				Constant.mapMetalCredits.put(strMetal,iMetalPrice);
			}
		}
		return "\n";
	}
	
	public static String countHowMuch(String [] strData) {
		String strAlienSym = "";
		String strRomanSym = "";
		if (strData.length>=5) {
			for (int i=3;i<strData.length-1;i++) {				
				if ("".equals(getKeyFromMap("Alien",strData[i]))) {
					return Constant.strNoResult + "\n";
				} else {
					strRomanSym+=getKeyFromMap("Alien",strData[i]);
					if ("".equals(strAlienSym)) {
						strAlienSym = strData[i];
					} else {
						strAlienSym = strAlienSym + " " + strData[i];
					}
				}
			}
			int iNumber = convertSymNum(strRomanSym);
			if (iNumber<=0) {
				return Constant.strNoResult + "\n";
			} else {
				String strRes = Constant.strResMuch.replace("[roman]",strAlienSym.toLowerCase());
				strRes = strRes.replace("[value]",String.valueOf(iNumber));
				return strRes.toLowerCase()+"\n";
			}
		} else {
			return Constant.strNoResult + "\n";
		}
	}
	
	public static String countHowMany(String [] strData) {
		String strAlienSym = "";
		String strRomanSym = "";
		String strMetal = "";
		if (strData.length>=7) {
			if (!"GOLD".equals(strData[strData.length-2]) && !"SILVER".equals(strData[strData.length-2]) 
					&& !"IRON".equals(strData[strData.length-2])) {
				return Constant.strNoResult + "\n";
			} else {
				strMetal = strData[strData.length-2];
				for (int i=4;i<strData.length-2;i++) {
					if ("".equals(getKeyFromMap("Alien",strData[i]))) {
						return Constant.strNoResult + "\n";
					} else {
						strRomanSym+=getKeyFromMap("Alien",strData[i]);
						if ("".equals(strAlienSym)) {
							strAlienSym = strData[i];
						} else {
							strAlienSym = strAlienSym + " " + strData[i];
						}
					}
				}
			}
			int iNumber = convertSymNum(strRomanSym);
			if (iNumber<=0) {
				return Constant.strNoResult + "\n";
			} else {
				String strRes = Constant.strResMany.replace("[roman]",strAlienSym.toLowerCase());
				String strFirstLetter = strMetal.substring(0,1);
				strRes = strRes.replace("[metal]",strFirstLetter+strMetal.substring(1).toLowerCase());
				strRes = strRes.replace("[value]",String.valueOf(iNumber*Constant.mapMetalCredits.get(strMetal)));
				return strRes+"\n";
			}
		} else {
			return Constant.strNoResult + "\n";
		}
	}
}
