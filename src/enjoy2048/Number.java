package enjoy2048;

import java.util.HashMap;
import java.util.Map;


public enum Number {
	N2(2), N4(4), N8(8), N16(16), N32(32), 
	N64(64), N128(128), N256(256), N512(512), N1024(1024), 
	N2048(2048), N4096(4096), N8192(8192), Empty(0), OddNumber(-999);
	
	int value;
	
	Number(int value) { 
		this.value= value;
	}
	public int getValue(){
		return value;
	}
	public Number getNext(){
		return  toNumber(value*2);
	}
	@Override
	public String toString(){
		if (this == Empty) return ".";
		return Integer.toString(value);
	}
	
	public static Number toNumber(int i){
		return numberMap.get(i);
	}
	
	static Map<Integer, Number> numberMap= new HashMap<>();
	
	static {
		for(Number b : Number.values()){
			numberMap.put(b.value, b);
		}
	}
	
}
