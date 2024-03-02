package ml;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

public class StringUtils {
    //Bảng mã unicode dựng sẵn (mặc định)
    private static final Normalizer.Form sdtForm = Normalizer.Form.NFC;
    //Loại bỏ các ký tự đặc biệt trước
    private static final String REMOVED_CHARS = "v/v|\\(|\\)|\\\"|\\”|\\“|\\:|\\.|\\,|\\;|\\?|\\!|về\\s+việc"; //về việc
    private static final Pattern specialCharPattern = Pattern.compile(REMOVED_CHARS);
    //Chuẩn hóa bảng mã về unicode dựng sẵn (mặc định) và loại bỏ các ký tự đặc biệt ,.;!?
    public static String vnNormalize(String orgString) {
        String normalized_string = Normalizer.normalize(orgString, sdtForm);
        return specialCharPattern.matcher(normalized_string).replaceAll(" ");
//		return Normalizer.normalize(orgString, sdtForm);
    }

    //Chuẩn hóa dấu => Hiện test chưa đúng
    static Map<String, String> dictChar;
    static Character[][] vowelTable = {
            {'a', 'à', 'á', 'ả', 'ã', 'ạ'},
            {'ă', 'ằ', 'ắ', 'ẳ', 'ẵ', 'ặ'},
            {'â', 'ầ', 'ấ', 'ẩ', 'ẫ', 'ậ'},
            {'e', 'è', 'é', 'ẻ', 'ẽ', 'ẹ'},
            {'ê', 'ề', 'ế', 'ể', 'ễ', 'ệ'},
            {'i', 'ì', 'í', 'ỉ', 'ĩ', 'ị'},
            {'o', 'ò', 'ó', 'ỏ', 'õ', 'ọ'},
            {'ô', 'ồ', 'ố', 'ổ', 'ỗ', 'ộ'},
            {'ơ', 'ờ', 'ớ', 'ở', 'ỡ', 'ợ'},
            {'u', 'ù', 'ú', 'ủ', 'ũ', 'ụ'},
            {'ư', 'ừ', 'ứ', 'ử', 'ữ', 'ự'},
            {'y', 'ỳ', 'ý', 'ỷ', 'ỹ', 'ỵ'}
    };
    static Set<Character> vietnamChars;
    static Map<Character, Integer> vowelLookupRow = new HashMap<>();
    static Map<Character, Integer> vowelLookupColumn = new HashMap<>();

    static {
        dictChar = loadDictChar();
        for (int i = 0; i < vowelTable.length; i++) {
            for (int j = 0; j < vowelTable[i].length; j++) {
                vowelLookupRow.put(vowelTable[i][j], i);
                vowelLookupColumn.put(vowelTable[i][j], j);
            }
        }

        vietnamChars = new HashSet<>(Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
                'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I',
                'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'à', 'á', 'ả', 'ã',
                'ạ', 'ầ', 'ấ', 'ẩ', 'ẫ', 'ậ', 'ằ', 'ắ', 'ẳ', 'ẵ', 'ặ', 'è', 'é', 'ẻ', 'ẽ', 'ẹ', 'ề', 'ế', 'ể', 'ễ', 'ệ',
                'ì', 'í', 'ỉ', 'ĩ', 'ị', 'ò', 'ó', 'ỏ', 'õ', 'ọ', 'ô', 'ồ', 'ố', 'ổ', 'ỗ', 'ộ', 'ờ', 'ớ', 'ở', 'ỡ', 'ợ', 'ù',
                'ú', 'ủ', 'ũ', 'ụ', 'ừ', 'ứ', 'ử', 'ữ', 'ự', 'ỳ', 'ý', 'ỷ', 'ỹ', 'ỵ', 'À', 'Á', 'Ả', 'Ã', 'Ạ', 'Ầ', 'Ấ',
                'Ẩ', 'Ẫ', 'Ậ', 'Ằ', 'Ắ', 'Ẳ', 'Ẵ', 'Ặ', 'È', 'É', 'Ẻ', 'Ẽ', 'Ẹ', 'Ề', 'Ế', 'Ể', 'Ễ', 'Ệ', 'Ì', 'Í', 'Ỉ',
                'Ĩ', 'Ị', 'Ò', 'Ó', 'Ỏ', 'Õ', 'Ọ', 'Ô', 'Ồ', 'Ố', 'Ổ', 'Ỗ', 'Ộ', 'Ờ', 'Ớ', 'Ở', 'Ỡ', 'Ợ', 'Ù', 'Ú', 'Ủ', 'Ũ',
                'Ụ', 'Ừ', 'Ứ', 'Ử', 'Ữ', 'Ự', 'Ỳ', 'Ý', 'Ỷ', 'Ỹ', 'Ỵ', 'đ', 'Đ', 'ă', 'Ă', 'â', 'Â', 'ê', 'Ê', 'ô', 'Ô', 'ơ', 'Ơ', 'ư', 'Ư'));
    }

    private static Map<String, String> loadDictChar() {
        String[] char1252 = ("à|á|ả|ã|ạ|ầ|ấ|ẩ|ẫ|ậ|ằ|ắ|ẳ|ẵ|ặ|è|é|ẻ|ẽ|ẹ|ề|ế|ể|ễ|ệ|ì|í|ỉ|ĩ|ị|ò|ó|ỏ|õ|ọ|ồ|ố|ổ|ỗ|ộ|ờ|ớ|ở|ỡ|ợ|ù|" +
                "ú|ủ|ũ|ụ|ừ|ứ|ử|ữ|ự|ỳ|ý|ỷ|ỹ|ỵ|À|Á|Ả|Ã|Ạ|Ầ|Ấ|Ẩ|Ẫ|Ậ|Ằ|Ắ|Ẳ|Ẵ|Ặ|È|É|Ẻ|Ẽ|Ẹ|Ề|Ế|Ể|Ễ|Ệ|Ì|Í|Ỉ|Ĩ|Ị|Ò|Ó|Ỏ|Õ|Ọ|Ồ|Ố|Ổ|Ỗ" +
                "|Ộ|Ờ|Ớ|Ở|Ỡ|Ợ|Ù|Ú|Ủ|Ũ|Ụ|Ừ|Ứ|Ử|Ữ|Ự|Ỳ|Ý|Ỷ|Ỹ|Ỵ").split("\\|");
        String[] charUTF8 = ("à|á|ả|ã|ạ|ầ|ấ|ẩ|ẫ|ậ|ằ|ắ|ẳ|ẵ|ặ|è|é|ẻ|ẽ|ẹ|ề|ế|ể|ễ|ệ|ì|í|ỉ|ĩ|ị|ò|ó|ỏ|õ|ọ|ồ|ố|ổ|ỗ|ộ|ờ|ớ|ở|ỡ|ợ|ù|ú|" +
                "ủ|ũ|ụ|ừ|ứ|ử|ữ|ự|ỳ|ý|ỷ|ỹ|ỵ|À|Á|Ả|Ã|Ạ|Ầ|Ấ|Ẩ|Ẫ|Ậ|Ằ|Ắ|Ẳ|Ẵ|Ặ|È|É|Ẻ|Ẽ|Ẹ|Ề|Ế|Ể|Ễ|Ệ|Ì|Í|Ỉ|Ĩ|Ị|Ò|Ó|Ỏ|Õ|Ọ|Ồ|Ố|Ổ|Ỗ|" +
                "Ộ|Ờ|Ớ|Ở|Ỡ|Ợ|Ù|Ú|Ủ|Ũ|Ụ|Ừ|Ứ|Ử|Ữ|Ự|Ỳ|Ý|Ỷ|Ỹ|Ỵ").split("\\|");

        Map<String, String> dictChar = new HashMap<>();
        for (int i = 0; i < char1252.length; i++) {
            dictChar.put(char1252[i], charUTF8[i]);
        }
        return dictChar;
    }


    public static String convertChar1252ToUtf8(String sentence) {
        for (String key : dictChar.keySet()) {
            sentence = sentence.replaceAll(key, dictChar.get(key));
        }
        return sentence;
    }

    private static boolean isVietnamWord(String word) {
        /*
         * Kiểm tra có phải là từ tiếng việt, có dấu
         * Input word cần lowerCase nhé
         * */
        boolean hasAccent = false;
        int currentVowel = -1;
        for (int i = 0; i < word.length(); i++) {
            if (!vietnamChars.contains(word.charAt(i))) return false;
            if (vowelLookupRow.containsKey(word.charAt(i))) {
                if (currentVowel == -1)
                    currentVowel = i;
                else {
                    if (i - currentVowel != 1) return false;
                    currentVowel = i;
                }
                if (vowelLookupColumn.get(word.charAt(i)) > 0) {
                    if (hasAccent) return false; // Một từ có hai thanh dấu
                    hasAccent = true;
                }
            }
        }
        return hasAccent;
    }

    public static String correctVnAccentWord(String word) {
        word = word.toLowerCase();
        if (!isVietnamWord(word)) return word;

        char[] chars = word.toCharArray();
        int accentPosition = 0, x, y;
        boolean isQuOrGi = false;

        List<Integer> vowelsIndex = new ArrayList<>();
        for (int i = 0; i < chars.length; i++) {
            x = vowelLookupRow.getOrDefault(chars[i], -1);
            y = vowelLookupColumn.getOrDefault(chars[i], -1);

            if (x == -1) continue;
            else if (x == 9) { // qu
                if (i != 0 && chars[i - 1] == 'q') {
                    chars[i] = 'u';
                    isQuOrGi = true;
                }
            } else if (x == 5) { // gi
                if (i != 0 && chars[i - 1] == 'g') {
                    chars[i] = 'i';
                    isQuOrGi = true;
                }
            }
            if (y != 0) {
                accentPosition = y;
                chars[i] = vowelTable[x][0];
            }
            if (!isQuOrGi || i != 1) {
                vowelsIndex.add(i);
            }
        }
        if (vowelsIndex.size() < 2) {
            if (isQuOrGi) {
                if (chars.length == 2) {
                    x = vowelLookupRow.get(chars[1]);
                    chars[1] = vowelTable[x][accentPosition];
                } else {
                    x = vowelLookupRow.getOrDefault(chars[2], -1);
                    if (x != -1) {
                        chars[2] = vowelTable[x][accentPosition];
                    } else {
                        chars[1] = (chars[1] == 'i' ? vowelTable[5][accentPosition] : vowelTable[9][accentPosition]);
                    }
                }
                return String.copyValueOf(chars);
            }
            return word;
        }
        for (int index : vowelsIndex) {
            x = vowelLookupRow.get(chars[index]);
            if (x == 4 || x == 8) { // ê, ơ
                chars[index] = vowelTable[x][accentPosition];
                return String.copyValueOf(chars);
            }
        }
        if (vowelsIndex.size() == 2) {
            if (vowelsIndex.get(vowelsIndex.size() - 1) == chars.length - 1) {
                x = vowelLookupRow.get(chars[vowelsIndex.get(0)]);
                chars[vowelsIndex.get(0)] = vowelTable[x][accentPosition];
            } else {
                x = vowelLookupRow.get(chars[vowelsIndex.get(1)]);
                chars[vowelsIndex.get(1)] = vowelTable[x][accentPosition];
            }
        } else {
            x = vowelLookupRow.get(chars[vowelsIndex.get(1)]);
            chars[vowelsIndex.get(1)] = vowelTable[x][accentPosition];
        }
        return String.copyValueOf(chars);
    }

    private static List<Boolean> getUpperState(String word) {
        List<Boolean> uppers = new ArrayList<>();
        for (char c : word.toCharArray()) {
            uppers.add(Character.isUpperCase(c) ? true : false);
        }
        return uppers;
    }

    private static String updateUpperState(String word, List<Boolean> uppers) {
        char[] chars = word.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            chars[i] = uppers.get(i) ? Character.toUpperCase(chars[i]) : chars[i];
        }
        return String.copyValueOf(chars);
    }

    public static String correctVnAccentSentence(String sentence) {
        String[] words = sentence.split("\\s+");
        for (int i = 0; i < words.length; i++) {
            List<Boolean> uppers = getUpperState(words[i]);
            words[i] = updateUpperState(correctVnAccentWord(words[i]), uppers);
        }
        return String.join(" ", words);
    }

}

