package weather.cs4985.westga.edu.thundercloud;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.List;

/**
 * Created by onejo on 1/24/2018.
 */

public class EntryAdapter extends ArrayAdapter<Entry> {

    private Context context;
    private int resource;
    private List<Entry> list;

    public EntryAdapter(Context context, int resource, List<Entry> list) {
        super(context, resource, list);
        this.context = context;
        this.resource = resource;
        this.list = list;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(this.context);
        View entryView = inflater.inflate(R.layout.entry_view, parent, false);
        Entry entry = this.list.get(position);

        TextView text = entryView.findViewById(R.id.textviewname);
        text.setText(entry.getName() + "         " + entry.getTemperature() + "\u00b0" + "F");
        TextView shortforecast = entryView.findViewById(R.id.textviewsize);
        shortforecast.setText(entry.getShortForecast());

        ImageView img = entryView.findViewById(R.id.button);
        try {
            //int resource = Integer.parseInt(entry.getImageResource());
            img.setImageDrawable(this.loadImageFromURL(entry.getImageResource()));

        } catch (Exception e) {
            e.getStackTrace();
        }

        return entryView;
    }

    public Drawable loadImageFromURL(String url){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().build();
        StrictMode.setThreadPolicy(policy);
        try{
            InputStream stream = (InputStream) new URL(url).getContent();
            Drawable drawable = Drawable.createFromStream(stream,"icon");
            return drawable;
        }catch(Exception e){
            return null;
        }
    }
    /**
    private int determineIcon(Entry entry) {

        int index = entry.getPath().lastIndexOf(".");
        String extension = entry.getPath().substring(index + 1, entry.getPath().length());
        System.out.println(extension);

        if (entry.isDirectory()) {
            return R.drawable.folder_icon;
        }
        if (extension.contains("txt")) {
            return R.drawable.text_icon;

        }
        if (extension.contains("mp3")) {
            return R.drawable.music_icon;

        }
        if (extension.contains("mp4")) {
            return R.drawable.movies_icon;

        }
        if (extension.contains("jpg") || extension.contains("png") || extension.contains("bmp")) {
            return R.drawable.photos_icon;

        }
        if (extension.contains("pdf")) {
            return R.drawable.pdf_icon;

        }
        if (extension.contains("docx")) {
            return R.drawable.document_icon;

        }
        if (extension.contains("zip")) {
            return R.drawable.zip_icon;

        }
        if (extension.contains("pptx")) {
            return R.drawable.powerpoint_icon;

        }
        return R.drawable.file_icon;
    }**/

    private Bitmap getBitmap(String path) {

        Bitmap imgBitmap;
        try {

            final int THUMBNAIL_SIZE = 64;

            FileInputStream inputStream = new FileInputStream(path);
            imgBitmap = BitmapFactory.decodeStream(inputStream);

            imgBitmap = Bitmap.createScaledBitmap(imgBitmap,
                    THUMBNAIL_SIZE, THUMBNAIL_SIZE, false);

            ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
            imgBitmap.compress(Bitmap.CompressFormat.PNG, 25, byteOutputStream);


        } catch (Exception ex) {
            return null;
        }
        return imgBitmap;
    }
}
