package com.example.wordladder;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

@RestController
public class Word_ladder {
    private static HashMap<String, ArrayList<String>> wordMap = new HashMap<String, ArrayList<String>>();
    private static void getOutput(String start_word, List<String> path, ArrayList<String> node, List<List<String>> output){
        for (int i = 0; i < node.size(); i++) {
            path.add(0, node.get(i));
            if(node.get(i).equals(start_word)){
                List<String> onePath = new LinkedList<String>(path);
                output.add(onePath);
            }
            else{
                getOutput(start_word, path, wordMap.get(node.get(i)), output);
            }
            path.remove(0);
        }
    }
    public static HashSet<String> readFileByLines(String fileName){
        File file = new File(fileName);
        BufferedReader reader = null;
        HashSet<String> dictionary = new HashSet<String>();
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
                dictionary.add(tempString);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                }
                catch (IOException e1) {
                }
            }
        }
        return dictionary;
    }
    public static int wordLadder(String start_word, String end_word, HashSet<String> dictionary, List<List<String>>output){
        int slength = start_word.length();
        int elength = end_word.length();
        if( slength != elength || slength == 0 ){
            return 0;
        }
        if(!dictionary.contains(start_word)||!dictionary.contains(end_word)){
            return 0;
        }
        Queue<String> theQueue = new LinkedList<String>();
        theQueue.offer(start_word);
        HashMap<String, Integer> word_level = new HashMap<String, Integer>();
        word_level.put(start_word, 0);
        wordMap.put(start_word, new ArrayList<String>());
        wordMap.put(end_word, new ArrayList<String>());
        HashSet<String> wordReached = new HashSet<String>();
        String oneWord, newWord;
        while(!theQueue.isEmpty()){
            oneWord = theQueue.poll();
            wordReached.add(oneWord);
            int level = word_level.get(oneWord);
            for (int i = 0; i < oneWord.length(); i++) {
                char wordArray[] = oneWord.toCharArray();
                for (char j = 'a'; j <= 'z'; j++) {
                    wordArray[i] = j;
                    newWord = new String(wordArray);
                    if(newWord.equals(end_word)){
                        if(!word_level.containsKey(end_word)){
                            word_level.put(end_word, level + 1);
                            wordMap.get(end_word).add(oneWord);
                        }
                        else if(word_level.get(end_word) == level + 1){
                            wordMap.get(end_word).add(oneWord);
                        }
                    }
                    else if(dictionary.contains(newWord) && !wordReached.contains(newWord)){
                        if(!word_level.containsKey(newWord)){
                            theQueue.offer(newWord);
                            word_level.put(newWord, level + 1);
                            ArrayList<String> words_linked = new ArrayList<String>();
                            words_linked.add(oneWord);
                            wordMap.put(newWord, words_linked);
                        }
                        else if(word_level.get(newWord) == level + 1){
                            wordMap.get(newWord).add(oneWord);
                        }
                    }
                }
            }
        }
        List<String> path = new ArrayList<String>();
        path.add(end_word);
        getOutput(start_word, path, wordMap.get(end_word), output);
        return 1;
    }
    public static void main(String[] args){
        HashSet<String> dict = readFileByLines("/Users/apple/IdeaProjects/wordladder/src/main/resources/EnglishWords.txt");
        List<List<String>> output = new ArrayList<List<String>>();
        Scanner sc = new Scanner(System.in);
        System.out.print("Start:");
        String start = sc.next();
        System.out.print("End:");
        String end = sc.next();
        int ret = wordLadder(start,end, dict, output);
        if(output.isEmpty()||ret == 0){
            System.out.println("No path!");
        }
        for (int i = 0; i < output.size(); i++) {
            List<String> temp = output.get(i);
            for (int j = 0; j < temp.size(); j++) {
                String word = temp.get(j);
                System.out.printf("%s  ", word);
            }
            System.out.println();
        }
    }
    @RequestMapping(value = "/ladder", method = RequestMethod.GET)
    public String say(@RequestParam("start") String start, @RequestParam("end") String end){
        StringBuilder result = new StringBuilder("|| ");
        HashSet<String> dict = readFileByLines("../../../resources/EnglishWords.txt");
        List<List<String>> output = new ArrayList<List<String>>();
        int ret = wordLadder(start, end, dict, output);
        if(output.isEmpty()||ret == 0){
            result = new StringBuilder("No path!");
        }
        for (int i = 0; i < output.size(); i++) {
            List<String> temp = output.get(i);
            for (int j = 0; j < temp.size(); j++) {
                String word = temp.get(j);
                result.append(word);
                result.append(" ");
            }
            result.append("|| ");
        }
        return result.toString();
    }

    @RequestMapping(value = "/haha", method = RequestMethod.GET)
    public String haha(){
        return "haha";
    }
}