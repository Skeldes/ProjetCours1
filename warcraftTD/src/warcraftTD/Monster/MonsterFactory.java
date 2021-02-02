package warcraftTD.Monster;

import java.util.List;

import warcraftTD.Position;

public class MonsterFactory {

	private static MonsterFactory self = null;
	
	private MonsterFactory() {
	}
	
	public static MonsterFactory getFactory() {
		if(self == null) self = new MonsterFactory();
		return self;
	}
	
	public Monster createMonster(String m, List<Position> p ) {
		Monster res = null;
		switch(m) {
		case "1":
			res = new BaseMonster(p);
			break;
		case "2":
			res = new FlightMonster(p);
			break;
		case "3":
			res = new BossSolMonster(p);
			break;
		case "4":
			res = new BossAirMonster(p);
		}
		return res;
	}
}
