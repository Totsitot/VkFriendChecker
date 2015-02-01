package com.albertik.utils.yandex;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by Beta on 2/1/15.
 */
public class SpellChecker {
    public static class Flags
    {
        public static final int IGNORE_UPPERCASE=1;//	Пропускать слова, написанные заглавными буквами, например, "ВПК".
        public static final int IGNORE_DIGITS=2;//	Пропускать слова с цифрами, например, "авп17х4534".
        public static final int IGNORE_URLS=4;//=Пропускать интернет-адреса, почтовые адреса и имена файлов.
        public static final int IGNORE_UDU=7;//1+2+4
        public static final int FIND_REPEAT_WORDS=8;//	Подсвечивать повторы слов, идущие подряд. Например, "я полетел на на Кипр".
        public static final int IGNORE_LATIN=16;//	Пропускать слова, написанные латиницей, например, "madrid".
        public static final int NO_SUGGEST=32;//	Только проверять текст, не выдавая вариантов для замены.
        public static final int FLAG_LATIN=128;//	Отмечать слова, написанные латиницей, как ошибочные.
        public static final int BY_WORDS=256;//	Не использовать словарное окружение (контекст) при проверке. Опция полезна в случаях, когда на вход сервиса передается список отдельных слов.
        public static final int IGNORE_CAPITALIZATION=512;//	Игнорировать неверное употребление ПРОПИСНЫХ/строчных букв, например, в слове "москва".
        public static final int IGNORE_ROMAN_NUMERALS=2048;//
    }

    public static final String lang = "ru";
    public String correct(final String text){
         final int 	ERROR_UNKNOWN_WORD=1;//	Слова нет в словаре.
         final int 	ERROR_REPEAT_WORD=2;//	Повтор слова.
        final int 	ERROR_CAPITALIZATION=3;//	Неверное употребление прописных и строчных букв.
        final int 	ERROR_TOO_MANY_ERRORS=4;//	Текст содержит слишком много ошибок. При этом приложение может отправить Яндекс.Спеллеру оставшийся непроверенным текст в следующем запросе.


        YandexRequestMaker yandexRequestMaker = new YandexRequestMaker();
        try {
            JSONArray response = yandexRequestMaker.createRequest("ru", Flags.IGNORE_UDU, text);
            String toReturn =text;
            int offset = 0;
            for(int i=0;i<response.length();i++){
                JSONObject object = response.getJSONObject(i);
                int code = object.getInt("code");

                switch (code){
                    case ERROR_CAPITALIZATION:
                    case ERROR_UNKNOWN_WORD:
                        String left = toReturn
                                .substring(0,object.getInt("pos")+offset);
                        String mid = "";
                        if(object.getJSONArray("s").length()>0){
                            mid=object.getJSONArray("s").getString(0);
                        }
                        int rigth_index = object.getInt("pos")+object.getInt("len")+offset;
                        String right = toReturn.substring(rigth_index);
                        toReturn = left+mid+right;
                        offset+= mid.length() - object.getString("word").length();
                        break;
                    case ERROR_REPEAT_WORD:
                        String left_word = toReturn
                                .substring(0,object.getInt("pos")+offset);
                        int rigth_index_word = object.getInt("pos")+object.getInt("len")+offset;
                        String right_word = toReturn.substring(rigth_index_word,toReturn.length()-rigth_index_word);
                        toReturn = left_word+right_word;
                        offset-=object.getString("word").length();
                        break;
                    default:
                        break;
                }
                System.out.println(toReturn);


            }
            return toReturn;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return "";
    }

}
