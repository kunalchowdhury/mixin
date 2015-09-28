package com.tst.test.collection;


import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

/**
 * Created by Kunal Chowdhury on 9/27/2015.
 */
public class EncodingTree {

    private static final int[] CHAR_ENCODING = new int[256];

    static {
        for (char c = 'a'; c <= 'z'; c++) {
            convert(c);
        }

        for (char c = 'A'; c <= 'Z'; c++) {
            convert(c);
        }

    }

    private static void convert(char c) {
        switch (c) {
            case 'e':
            case 'E':
                CHAR_ENCODING[c] = 0;
                break;
            case 'j':
            case 'n':
            case 'q':
            case 'J':
            case 'N':
            case 'Q':
                CHAR_ENCODING[c] = 1;
                break;
            case 'r':
            case 'w':
            case 'x':
            case 'R':
            case 'W':
            case 'X':
                CHAR_ENCODING[c] = 2;
                break;
            case 'd':
            case 's':
            case 'y':
            case 'D':
            case 'S':
            case 'Y':
                CHAR_ENCODING[c] = 3;
                break;
            case 'f':
            case 't':
            case 'F':
            case 'T':
                CHAR_ENCODING[c] = 4;
                break;
            case 'a':
            case 'm':
            case 'A':
            case 'M':
                CHAR_ENCODING[c] = 5;
                break;
            case 'c':
            case 'i':
            case 'v':
            case 'C':
            case 'I':
            case 'V':
                CHAR_ENCODING[c] = 6;
                break;
            case 'b':
            case 'k':
            case 'u':
            case 'B':
            case 'K':
            case 'U':
                CHAR_ENCODING[c] = 7;
                break;
            case 'l':
            case 'o':
            case 'p':
            case 'L':
            case 'O':
            case 'P':
                CHAR_ENCODING[c] = 8;
                break;
            case 'g':
            case 'h':
            case 'z':
            case 'G':
            case 'H':
            case 'Z':
                CHAR_ENCODING[c] = 9;
                break;

        }
    }

    private EncodingTreeNode root;

    private static class EncodingTreeNode {
        private char c;
        private EncodingTreeNode left, mid, right;
        private Queue queue;
    }

    public void put(char[] s, String val) {
        root = put(root, s, val, 0);
    }

    public Queue get(char[] key) {
        if (key == null) throw new NullPointerException();
        if (key.length == 0) throw new
                IllegalArgumentException("key must have length >= 1");
        EncodingTreeNode x = get(root, key, 0);
        if (x == null) return null;
        return x.queue;
    }

    private Map<Integer, Queue> indexes = new TreeMap<Integer, Queue>();

    private EncodingTreeNode get(EncodingTreeNode x, char[] key, int d) {
        if (key == null) throw new NullPointerException();
        if (key.length == 0) throw new
                IllegalArgumentException("key must have length >= 1");
        if (x == null) {
            if (d < key.length) {
                return get(root, key, d);
            }
            return null;
        }
        char c = key[d];
        if (c < x.c) return get(x.left, key, d);
        else if (c > x.c) return get(x.right, key, d);
        else if (d < key.length - 1) {
            if (x.queue != null) {
                indexes.put(d, x.queue);
            }
            return get(x.mid, key, d + 1);
        } else {
            if (x.queue != null) {
                indexes.put(d, x.queue);
            }
            return x;
        }
    }

    private EncodingTreeNode put(EncodingTreeNode x,
                                 char[] s, String val, int d) {
        char c = s[d];
        if (x == null) {
            x = new EncodingTreeNode();
            x.c = c;
        }
        if (c < x.c) {
            x.left = put(x.left, s, val, d);
        } else if (c > x.c) {
            x.right = put(x.right, s, val, d);
        } else if (d < s.length - 1) {
            x.mid = put(x.mid, s, val, d + 1);
        } else {
            if (x.queue == null) {
                x.queue = new Queue();
            }
            x.queue.add(val);
        }
        return x;
    }

    public static void main2(String[] args) {
        Set<String> s = new HashSet<String>();
        s.add("1");
        s.add("2");
        s.add("3");

        Set<String> s1 = new HashSet<String>();
        s1.add("4");
        s1.add("5");
        s1.add("6");

        Set<String> s2 = new HashSet<String>();
        s2.add("7");
        s2.add("8");
        s2.add("9");
        s2.add("0");

        Set[] ss = new Set[]{s, s1, s2};

        cartesian(ss, 0, new String[ss.length]);
    }

    private static void cartesian(Set[] ss, int k,
                                  String[] s) {
        if (k > ss.length ) {
            return;
        }
        if(k == ss.length-1){
            for (String j : (Set<String>) ss[k]) {
                s[k] = j;
                System.out.println(Arrays.toString(s));
            }
        }else {
            for (String j : (Set<String>) ss[k]) {
                s[k] = j;
                cartesian(ss, k + 1, s);
            }
        }
    }

    private static final Map<String, List<StringBuilder>>
            result = new HashMap<String, List<StringBuilder>>();


    private static final List<String> temp = new ArrayList<String>();

    public static void main(String[] args) throws Exception {
        FileReader fr = new FileReader("D:\\dictionary.txt");
        BufferedReader br = new BufferedReader(fr);
        String line;
        EncodingTree en = new EncodingTree();
        while ((line = br.readLine()) != null) {
            en.put(transformArray(line.toCharArray(), false), line);
        }
        System.out.println("saved dictionary");

        String phoneNumber = "5624-82" ;
        en.get(transformArray(phoneNumber.toCharArray(), true));
        result.clear();
        for (Map.Entry<Integer, Queue> e : en.indexes.entrySet()) {
            Queue q = e.getValue();
            construct(e.getKey(),q,phoneNumber);

            /*while (q.hasNext()) {
                System.out.print(e.getKey() + " --> " + q.next() + " ");

            }
            System.out.println();
*/      }
        System.out.println("---");
        //filterAndPrint(phoneNumber);
    }

    private static void filterAndPrint(String phnNum) {
        List<StringBuilder> sb = result.get(phnNum);
        Iterator<StringBuilder>  iterator = sb.iterator();
        while (iterator.hasNext()){
            if(!correct(iterator.next(), phnNum)){
                iterator.remove();
            }
        }
    }

    private static boolean correct(StringBuilder next, String phnNum) {
        return false;
    }

    private static final List<List> individualIndexes = new ArrayList<List>();

    private static void construct(int position,
                                    Queue q,
                                    String phoneNumber) {
        if(!result.containsKey(phoneNumber)){
            result.put(phoneNumber, new ArrayList<StringBuilder>());
        }

        List<StringBuilder> builders = result.get(phoneNumber);
        int i = 0;
        if(builders.isEmpty()){
            while (q.hasNext()) {
                String s = q.next();
                int startPos = position - s.length() + 1;
                StringBuilder sb = new StringBuilder(createEmptyString(phoneNumber));
                sb.insert(startPos, s);
                builders.add(sb);
                individualIndexes.add(i, new LinkedList<Integer>());
                individualIndexes.get(i).add(startPos);
                i++;
            }
        }else {
            temp.clear();
            while (q.hasNext()){
                temp.add(q.next());
            }
            merge(temp, builders, position);
        }

    }

    private static String createEmptyString(String str) {
        int length = 0;
        for (int i = 0; i < str.length(); i++) {
            if(Character.isLetter(str.charAt(i))){
                    length++;
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(" ");
        }
        return sb.toString();
    }

    private static void merge(List<String> temp,
                              List<StringBuilder> stringBuilders ,
                              int position) {
        List<StringBuilder> atemp = new ArrayList<StringBuilder>();
        for (StringBuilder s : stringBuilders){
            atemp.add(s);
        }

        int tempSz = atemp.size();
        int targetSize = temp.size()*stringBuilders.size();
        int pos = stringBuilders.size();

        while (stringBuilders.size() < targetSize) {
            stringBuilders.addAll(pos, atemp);
            pos+= tempSz;
        }
        //System.out.println(stringBuilders);
        int k = 0;
        for (int i = 0; i < temp.size(); i++) {
            for (int j = 0; j < tempSz; j++) {
                StringBuilder stringBuilder =
                        new StringBuilder(stringBuilders.get(k));
                String s = temp.get(i);
                int startPos = position - s.length() + 1;
                stringBuilder.insert(startPos, s);
                if(individualIndexes.size() <= k){
                    individualIndexes.add(k, new LinkedList<Integer>());
                }
                individualIndexes.get(k).add(startPos);
                stringBuilders.set(k++, stringBuilder);
            }
        }

      //  System.out.println(stringBuilders);


    }

    private static char[] transformArray(char[] chars,
                                         boolean transformToDigit) {
        int size = 0;
        for (char c : chars) {
            if ((transformToDigit && Character.isDigit(c))
                    || Character.isLetter(c)) {
                size++;
            }
        }
        char digitArray[] = new char[size];
        int i, j;
        i = j = 0;
        while (i < chars.length) {
            if (!transformToDigit && Character.isLetter(chars[i])) {
                digitArray[j++] = (char) (CHAR_ENCODING[chars[i]] + 48);
            } else if (transformToDigit && Character.isDigit(chars[i])) {
                digitArray[j++] = chars[i];
            }
            i++;
        }

        return digitArray;
    }


}
