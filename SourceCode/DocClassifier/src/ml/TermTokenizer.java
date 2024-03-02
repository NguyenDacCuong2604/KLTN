package ml;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import vn.pipeline.Annotation;
import vn.pipeline.Sentence;
import vn.pipeline.VnCoreNLP;
import vn.pipeline.Word;

public class TermTokenizer {
    /*  Không cần thiết do hàm chuẩn hóa đã loại bỏ các dấu ,.;!?
        private static final String[] annotators = {"wseg","pos"};
        //Lọc theo loại từ
        // CH - dấu , .
        // Nc - đại từ nhân xưng Ông, bà
        // M  - số
    */
    //MIN_LENGHT = 2; là tốt nhất => KHông điều chỉnh nữa
    private int MIN_LENGHT = 2; //term phải có độ dài tối thiểu = 2 ký tự
    //	private static final String[] excludePosTags = {"CH","Nc","M"};
    private static final String[] excludePosTags = {"CH","Nc","M"};
    private Set<String> posTagFilter;

    private StopWords stopWordRemover = null;
    private static final String[] annotators = {"wseg","pos"};
    VnCoreNLP coreNLP;

    private static final int MAX_GRAM = 3;
    int nGram = 1;
    private List<String> tokens;

    public TermTokenizer(StopWords stopwords, int nGram) throws IOException {
        this(nGram);
        this.stopWordRemover = stopwords;
    }
    public TermTokenizer(int nGram) throws IOException {
        this.nGram = nGram;
        if (nGram<1) this.nGram = 1;
        if (nGram>MAX_GRAM) this.nGram = MAX_GRAM;

        coreNLP = new VnCoreNLP(annotators);
        tokens = new ArrayList<>();

        posTagFilter = new HashSet<>();
        for(String tag:excludePosTags) posTagFilter.add(tag);
    }

    private List<String> ngramAtWordLevel(int n) {
        List<String> output = new ArrayList<>();
        for (int i = 0; i < this.tokens.size(); i++) {
            StringBuffer sb = new StringBuffer();
            if (i + n <= this.tokens.size()) {
                for (int j = i; j < i + n; j++)
                    sb.append(this.tokens.get(j) + " ");
                String ngram = sb.toString();
                output.add(ngram);
            }
        }
        return output;
    }

    private boolean isAccepted(Word word) {
        String term = word.getForm();
        if (posTagFilter.contains(word.getPosTag())) return false;
        if (term.length()<MIN_LENGHT) return false;
        if (stopWordRemover!=null) return stopWordRemover.isNotStopWord(term.toLowerCase());
//loại bỏ từ đơn
//		if (!term.contains("_")) return false;
        return true;
    }


    public List<String> getTokens(String document){
        tokens.clear();
        //chuyển bảng mã về unicode dựng sẵn + loại bõ ký tự .,;!?

        Annotation annotation = new Annotation(StringUtils.vnNormalize(document + " "));
        try {
            coreNLP.annotate(annotation);
        } catch (IOException e) {
            e.printStackTrace();
            return tokens;
        }
        //1. get All word
        for(Sentence sentence:annotation.getSentences()) {
            for (Word word : sentence.getWords()) {
                //2. remove stop words
                if (isAccepted(word)) tokens.add(word.getForm());
            }
        }

        //3. calculate 1-n gram tokens
        List<String> allGramTokens = new ArrayList<>();
        //1-gram
        allGramTokens.addAll(tokens);
        //n-gram
        List<String> nGramTokens;
        for (int n=2; n<=nGram; n++) {
            nGramTokens = ngramAtWordLevel(n);
            allGramTokens.addAll(nGramTokens);
        }
        return allGramTokens;
    }

    public static void print(List<String> documents){
        for(String text : documents){
            System.out.println(text);
        }
    }

    public static void main(String[] args) throws IOException {
        StopWords stopWords = new StopWords("D:\\KLTN\\Data\\StopWords\\vietnamese-stopwords-dash.txt", "UTF-8");
        TermTokenizer termTokenizer_StopWords = new TermTokenizer(stopWords, 1);
        List<String> listTest_StopWords = termTokenizer_StopWords.getTokens("Trong thế kỷ 21, công nghệ thông tin đang phát triển mạnh mẽ, góp phần thay đổi cuộc sống của mọi người. Internet đã trở thành một phần không thể thiếu trong cuộc sống hàng ngày. Việc tìm kiếm thông tin trên mạng trở nên dễ dàng hơn bao giờ hết. Cùng với đó, sự phát triển của các ứng dụng di động đã mang lại nhiều tiện ích mới cho người dùng. Tuy nhiên, cũng cần phải lưu ý đến các vấn đề liên quan đến bảo mật thông tin và quyền riêng tư khi sử dụng công nghệ mới này.");
        System.out.println(listTest_StopWords.size());
        print(listTest_StopWords);

        System.out.println("----");

        TermTokenizer termTokenizer = new TermTokenizer(1);
        List<String> listTest = termTokenizer.getTokens("Trong thế kỷ 21, công nghệ thông tin đang phát triển mạnh mẽ, góp phần thay đổi cuộc sống của mọi người. Internet đã trở thành một phần không thể thiếu trong cuộc sống hàng ngày. Việc tìm kiếm thông tin trên mạng trở nên dễ dàng hơn bao giờ hết. Cùng với đó, sự phát triển của các ứng dụng di động đã mang lại nhiều tiện ích mới cho người dùng. Tuy nhiên, cũng cần phải lưu ý đến các vấn đề liên quan đến bảo mật thông tin và quyền riêng tư khi sử dụng công nghệ mới này.");
        System.out.println(listTest.size());
        print(listTest);
    }
}

