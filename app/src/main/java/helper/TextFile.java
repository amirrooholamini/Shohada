package helper;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class TextFile {
    public static String readTextFile(Context ctx, int resId)

    {

        InputStream inputStream = ctx.getResources().openRawResource(resId);

        InputStreamReader inputreader = null;
        try {
            inputreader = new InputStreamReader(inputStream, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        BufferedReader bufferedreader = new BufferedReader(inputreader);

        String line;

        StringBuilder stringBuilder = new StringBuilder();

        try

        {

            while (( line = bufferedreader.readLine()) != null)

            {

                stringBuilder.append(line);

                stringBuilder.append('\n');

            }

        }

        catch (IOException e)

        {

            return null;

        }
        return stringBuilder.toString();

    }
}
