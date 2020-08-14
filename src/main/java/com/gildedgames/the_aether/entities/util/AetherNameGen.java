package com.gildedgames.the_aether.entities.util;

import java.util.Random;

public class AetherNameGen {

	public static Random rand = new Random();

	public static String[] valkNamePrefix = {"Har", "Her", "Gon", "Sko", "Hil"};

	public static String[] valkNameMiddix = {"fjo", "ska", "bri", "", ""};

	public static String[] valkNameSuffix = {"tur", "pul", "dul", "gul", "or"};

	public static String[] name1 = new String[]{"Del", "Ros", "Per", "Cyn",
			"Flar", "Ba", "Lab", "Az", "Ob", "Al", "Pas", "Nun", "Ur", "Ter",
			"Jar", "Est", "Car", "Houl", "In", "Kop", "Shot", "Vast", "Ael",
			"Skal", "Geld", "Er", "Sald", "Sar", "Binthe", "Haur", "Syn",
			"Kren", "Verd", "Pav", "Mart", "Yal", "Kor", "Lar", "Mel",
			"Claune", "For", "Rusk", "Pan", "Dir", "Chies", "Kil", "Odas",
			"Toc", "Plag", "Uld", "Yar", "Win", "Ry", "Harg", "Ty", "Druc",
			"Vald", "Keld", "Ar", "Das", "Klab", "Wearn", "Pes", "Art", "Na",
			"Harab", "Cal", "Ha", "Nev", "Wy", "Las", "Jes", "Kir", "Oc",
			"Brow", "Nist", "Var", "Lor", "Naut", "Hab", "Ard", "Vard", "Nor",
			"Tes", "Rol", "Ge", "Daf", "Tral", "Scaun", "Kol", "Star", "Ald",
			"Nap", "Loc", "Cor", "Chy", "Hy", "Har", "Char", "Bay", "Rald",
			"Yas", "Grave", "Dras", "Sep", "Shy", "Eth", "El", "Crac", "Hob",
			"Zol", "Bel", "Bar", "Zald", "Hal", "Ryle", "Obr", "Dain", "Jast",
			"Mold", "Bae", "As", "Ki", "Val", "Par", "Ash", "Ost", "Haith",
			"I", "Ca", "Vad", "Ro", "Dez", "Kuv", "Vab", "Dar", "Ral", "Mar",
			"Quarne", "Pulg", "Hor", "Ka", "Quer", "And", "Ny", "Ym", "Wal",
			"Tarl", "Vart", "Pro"};

	public static String[] name2 = new String[]{"cath", "yd", "rik", "nal",
			"y", "ro", "stead", "carn", "tak", "chean", "ain", "ak", "loc",
			"ras", "ien", "cuth", "alen", "is", "carad", "acy", "la", "asc",
			"arn", "ayn", "sene", "art", "lyn", "cam", "mel", "ly", "eas",
			"il", "oller", "bin", "a", "astar", "tuny", "iera", "aldy", "ene",
			"in", "yne", "id", "aur", "era", "fyn", "om", "sume", "ir", "que",
			"warty", "tan", "warne", "tar", "lene", "ron", "valer", "war",
			"ant", "iam", "ew", "am", "ime", "varn", "orn", "quent", "geth",
			"aver", "car", "lin", "luth", "as", "gant", "gine", "carth",
			"ayne", "asty", "arik", "lir", "on", "codd", "az", "tyne", "wat",
			"erry", "aryne", "ert", "quin", "rath", "ame", "tin", "ter",
			"quine", "arth", "arne", "eth", "zane", "gar", "an", "yn", "gayn",
			"ax", "ast", "azer", "ont", "or", "ian", "gur", "et", "er", "im",
			"dar", "end", "doth", "en", "aller", "reth", "juin", "arny", "un",
			"len", "ere", "ane", "aryn", "dy", "gary", "arry", "arty", "ydd",
			"ris", "ar", "ance", "hen", "el", "air", "rum"};

	public static String[] name3 = new String[]{"cath", "carth", "ayne",
			"yd", "arik", "asty", "rik", "lir", "dor", "nal", "on", "codd",
			"y", "ber", "ro", "stead", "ta", "tyne", "wat", "carn", "erry",
			"chean", "ain", "aryne", "al", "ak", "ert", "ras", "ien", "alen",
			"is", "acy", "la", "ame", "ter", "arn", "quine", "ayn", "sene",
			"art", "arth", "lyn", "mel", "arne", "ern", "eth", "ly", "zane",
			"se", "gar", "eas", "ty", "an", "il", "yn", "oller", "gayn", "a",
			"bin", "iel", "azer", "astar", "tuny", "ont", "iera", "aldy",
			"ene", "ian", "or", "in", "sa", "yne", "era", "er", "fyn", "im",
			"dar", "sume", "doth", "en", "aller", "ir", "reth", "que", "arny",
			"ney", "warty", "un", "juin", "nik", "warne", "ere", "yr", "lene",
			"ane", "valer", "war", "ant", "iam", "ew", "dy", "aryn", "gary",
			"ime", "varn", "orn", "tha", "arty", "quent", "arry", "aver",
			"geth", "car", "lin", "ydd", "ris", "nyd", "ance", "luth", "ar",
			"dal", "gant", "el", "tyn", "air", "gine", "boo"};

	public static String gen() {
		String name = name1[rand.nextInt(name1.length)];

		int middle = 2 + rand.nextInt(2);
		for (int i = 0; i < middle; i++)
			name += name2[rand.nextInt(name2.length)];

		return name + name3[rand.nextInt(name3.length)];
	}

	public static String valkGen() {

		String result = "";

		result += valkNamePrefix[rand.nextInt(valkNamePrefix.length)];
		result += valkNameMiddix[rand.nextInt(valkNameMiddix.length)];
		result += valkNameSuffix[rand.nextInt(valkNameSuffix.length)];

		return result;
	}

}