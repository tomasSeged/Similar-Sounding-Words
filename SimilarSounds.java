import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Add your description here.
 */
class SimilarSounds
{
	// ******DO NO CHANGE********//

	/**
	 * wordToSound maps each word to its corresponding sound.
     */
	static Map<String, String> wordToSound;

	/**
	 * soundGroupToSimilarWords maps each sound-group to a BST containing all the words that share that sound-group.
     */
	static Map<String, BST<String>> soundGroupToSimilarWords;

	/**
	 * Do not change.
	 * @param words one or more words passed on the command line.
     */
	public static void processWords(String words[]) {

		ArrayList<String> lines = (ArrayList<String>)Extractor.readFile("word_to_sound.txt");
		populateWordToSoundMap(lines);
		populateSoundGroupToSimilarWordsMap(lines);


		if (words.length >= 2) {
			// check which of the words in the list have matching sounds
			findSimilarWordsInList(words);
		} else if (words.length == 1) {
			// get the list of words with matching sounds as this word
			findSimilarWordsTo(words[0]);
		}

	}

	/**
	 *  Main Method.
	 *
	 *  @param args args
	 */
	public static void main(String args[]) {

		if (args.length == 0) {
			System.out.println("Wrong number of arguments, expecting:");
			System.out.println("java SimilarSounds word1 word2 word3...");
			System.out.println("java SimilarSounds word");
			System.exit(-1);
		}

		wordToSound = new java.util.HashMap<>(); // maps <word, sound>
        soundGroupToSimilarWords = new java.util.HashMap<>(); // maps <sound-group, sorted list of words with similar sounds>

		processWords(args);

	}
	// ******DO NO CHANGE********//

	/**
	 * Given a list of all entries in the database, this method populates the wordToSound map
	 * as follows: the key is the word, and the value is the sound (i.e., the sequence of unisounds)
	 * For example, if the line entry is "moderated M AA1 D ER0 EY2 T IH0 D", the key would be "moderated"
	 * and the value would be "M AA1 D ER0 EY2 T IH0 D"
	 * To achieve this, you need to use the methods in the Extractor class
	 * @param lines lines
	 */
	public static void populateWordToSoundMap(List<String> lines) {
		for(int i=0; i<lines.size(); i++){
			wordToSound.put(Extractor.extractWordFromLine(lines.get(i)), Extractor.extractSoundFromLine(lines.get(i)));
		}
	}

	/**
	 * Given a list of all entries in the database, this method populates the
	 * soundGroupToSimilarWords map as follows: the key is the sound-group,
	 * and the value is a BST containing all the words that share that sound-group.
	 * For example, if the line entry is "moderated M AA1 D ER0 EY2 T IH0 D", the key would
	 * be "EY2 T IH0 D" and the value would be a BST containing "moderated" and all other
	 * words in the database that share the sound-group "EY2 T IH0 D"
	 * To achieve this, you need to use the methods in the Extractor class
	 * @param lines content of the database
	 */
	public static void populateSoundGroupToSimilarWordsMap(List<String> lines){

		/*
			for(int i=0; i<lines.size(); i++){
				BST<String> tree1 = new BST<>();

				for(int j=0; j<lines.size(); j++){

					if(Extractor.extractSoundGroupFromSound(Extractor.extractSoundFromLine(lines.get(i))).equals(Extractor.extractSoundGroupFromSound(Extractor.extractSoundFromLine(lines.get(j))))){
						tree1.insert(Extractor.extractWordFromLine(lines.get(j)));
					}
				}
				soundGroupToSimilarWords.put(Extractor.extractSoundGroupFromSound(Extractor.extractSoundFromLine(lines.get(i))),tree1);
			}

		ArrayList<String> SoundGroupSet = new ArrayList<>();
		*/
	}

	/**
	 * Given a list of words, e.g., [word1, word2, word3, word4], this method checks whether
	 * word1 is similar to word2, word3, and word4. Then checks whether word2 is similar
	 * to word3 and word4, and finally whether word3 is similar to word4.
	 *
	 * <p>For example if the list contains: [calculated legislated hello world miscalleneous
	 * miscalculated encapsulated LIBERATED Sophisticated perculated hello],
	 * the output should exactly be as follows:
	 *
	 * <p>"calculated" sounds similar to: "legislated"
	 *	"hello" sounds similar to: none
	 *	"world" sounds similar to: none
	 *	"miscalculated" sounds similar to: "encapsulated" "LIBERATED" "Sophisticated"
	 *	Unrecognized words: "miscalleneous" "perculated"
     *
     * 	<p>Note however that:
	 * a) if a word was already found similar, then it will be ignored hereafter
	 * b) the behavior is case insensitive
	 * c) the subsequent occurrence of a given word is ignored
	 * d) words that couldn’t be found in the database are deemed unrecognizable
	 * e) words are displayed within quotes
	 * @param words list of words to examine
	 */
	public static void findSimilarWordsInList(String words[]) {

		//convert array to arrayList.
		ArrayList<String> w = new ArrayList<>();
		for(String s: words){
			w.add(s);
		}

		//remove duplicates.
		ArrayList<String> wordList = new ArrayList<>();
		for(String s: w){
			if(!wordList.contains(s))
				wordList.add(s);
		}

		//arrayList for unrecognized words.
		ArrayList<String> unrecog = new ArrayList<>();

		//if wordToSound map doesn't contain a word in the list, add to unrecog list and replace with "" in the original list.
		for(int i=0; i<wordList.size(); i++){
			if(wordToSound.containsKey(wordList.get(i).toUpperCase())!= true){
				unrecog.add(wordList.get(i));
				wordList.set(i,"");
			}
		}

		String similarFinal = ""; //String to be used at the end to printout the similar words(if any) in the format required.

		for(int i=0; i<wordList.size(); i++) {

			ArrayList<String> similar = new ArrayList<>(); //arrayList to keep the similar words for each word/iteration.
			similarFinal = "";
			for (int j = i + 1; j < wordList.size(); j++){
				if(!wordList.get(i).equals("") && !wordList.get(j).equals("") && Extractor.extractSoundGroupFromSound(wordToSound.get(wordList.get(i).toUpperCase())).equals(Extractor.extractSoundGroupFromSound(wordToSound.get(wordList.get(j).toUpperCase())))){
					similar.add(wordList.get(j));
					wordList.set(j,"");
				}
			}

			if(!wordList.get(i).equals("")) {

				for(String a : similar){
					similarFinal += "\"" + a + "\" ";
				}
				if(similarFinal.length()>0){
					similarFinal = similarFinal.substring(0, similarFinal.length()-1); //Remove trailing space.
					System.out.println("\"" + wordList.get(i) + "\" sounds similar to: " + similarFinal);
				}
				else //If there are no similar words.
					System.out.println("\"" + wordList.get(i) + "\" sounds similar to: none");
			}
			wordList.set(i,"");
		}

		//If there are any words left in the list(unrecognized), add to unrecog.
		for(int i=0; i<wordList.size(); i++){
			if(!wordList.get(i).equals("")) {
				unrecog.add(wordList.get(i));
			}
		}

		String unrecognized = ""; //String to be used at the end to printout the unrecognized words(if any) in the format required.
		for(String s : unrecog){
			unrecognized += "\"" + s + "\" ";
		}

		if(unrecognized.length()>0){
			unrecognized = unrecognized.substring(0, unrecognized.length()-1); //Remove trailing space
			System.out.println("Unrecognized words: " + unrecognized);
		}
		else{
			System.out.println("Unrecognized words: none");
		}
	}

	/**
	 * Given a passed word this method prints all similarly sounding words in ascending order (including the passed word)
	 * For example:	java SimilarSounds dimension
	 * Words similar to "dimension": "ASCENSION" "ATTENTION" "CONTENTION" "CONVENTION" "DECLENSION"
	 * "DETENTION" "DIMENSION" "DISSENSION" "EXTENSION" "GENTIAN" "HENSCHEN" "LAURENTIAN"
	 * "MENTION" "PENSION" "PRETENSION" "PREVENTION" "RETENTION" "SUSPENSION" "TENSION"
     *
	 * <p>Note how the word passed as an argument must still appear in the output.
	 * However, if it cannot be found in the database an appropriate error message should be displayed
	 * @param theWord word to process
	 */
	public static void findSimilarWordsTo(String theWord) {

		if(!wordToSound.containsKey(theWord.toUpperCase()))
		{
			System.out.println("Unrecognized word: \"" + theWord + "\"");
			return;
		}

		String leSound = wordToSound.get(theWord.toUpperCase()); // leSound = sound of word inputted
		String theSoundGroup = Extractor.extractSoundGroupFromSound(leSound); //soundGroup of the word
		ArrayList<String> simWords = new ArrayList<>();

		//loop through wordToSound, add the keys of those with the same value(soundGroup)

		for (Map.Entry<String, String> e : wordToSound.entrySet()) {
			if (Extractor.extractSoundGroupFromSound(e.getValue()).equals(theSoundGroup)) {
				simWords.add(e.getKey());
			}
		}

		sortString(simWords); //call helper function to sort list words alphabetically
		String[] similarArray = simWords.toArray(new String[0]); //convert to string array
		String wordsSimilar = "";

		for (String s : similarArray) {
			wordsSimilar += "\"" + s + "\" ";
		}
		wordsSimilar = wordsSimilar.substring(0, wordsSimilar.length() - 1); //remove the trailing space
		System.out.println("Words similar to \"" + theWord + "\": " + wordsSimilar); //printout the list of similar words

	}

	/**
	 * A helper function to sort out an arraylist of type string alphabetically.
	 * @param str list of type string
	 */
	public static void sortString(ArrayList<String> str)
	{
		for(int i=0; i<str.size()-1; i++){
			for(int j=i+1; j<str.size(); j++){
				if(str.get(i).compareTo(str.get(j))>0)
				{
					String hold = str.get(i);
					str.set(i, str.get(j));
					str.set(j, hold);
				}
			}
		}
	}
}