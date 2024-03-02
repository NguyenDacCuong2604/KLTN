package ml;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

/* File chứa các Stop Words phải lưu theo bảng mả UTF-8
 * Mỗi stop words là 1 dòng
 * Stop word có thể là 1 cụm từ ngay cách nhau bởi dấu khoảng trắng
 */
public class StopWords {
    Set<String> stopWordSet =  new HashSet<>();

    //load từ file vietnamese-stopwords-dash.txt
    //mỗi dòng là 1 word
    public StopWords(String fileName, String charset) throws IOException {
        loadStopWord(fileName, charset);
    }

    public void loadStopWord(String fileName, String charset) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName),charset));
        String line;

        while ((line = br.readLine())!=null) {
            line = line.trim();
            if (line.isEmpty()) continue;
            stopWordSet.add(line)	;
        }
        br.close();
    }

    public boolean isStopWord(String word) {
        return stopWordSet.contains(word);
    }

    public boolean isNotStopWord(String word) {
        return !stopWordSet.contains(word);
    }

    public static void main(String[] args) throws IOException {
        StopWords  stw = new StopWords("D:\\KLTN\\Data\\StopWords\\vietnamese-stopwords-dash.txt", "UTF-8");
        String word =  "về_việc";
        System.out.println(stw.isStopWord(word));
    }
}
