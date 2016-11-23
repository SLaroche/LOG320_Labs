package util;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Pos2D {
	public Pos2D(int x, int y) {
		this.x = x;
		this.y = y;
	}
	public int x;
	public int y;
	
	@Override
	public int hashCode()
	{
	    return (x*10)+y;
	}
	
	@Override
	public boolean equals(Object o) {
		Pos2D pos = (Pos2D)o;
		if (this.x==pos.x&&this.y==pos.y) {
			return true;
		}
	    return false;
	}
	
	@Override
	public String toString() {
		return x+""+y;
	}
}
