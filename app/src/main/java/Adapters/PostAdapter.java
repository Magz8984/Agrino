package Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.collins.agrino.R;

public class PostAdapter extends BaseAdapter {
    Context context;

    public  String[]  letters=new String[]{
        "S","P","C"
    };

    public  String[] services=new String[]{
        "Services","Produce","Chats"
    };


    public  PostAdapter(Context context){
        this.context=context;
    }

    @Override
    public int getCount() {
        return letters.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater=(LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View gridView;
         if(convertView == null){
             gridView=inflater.inflate(R.layout.post_item,null);
             TextView lblLetter=(TextView)  gridView.findViewById(R.id.lbletter);
             TextView lblHint=(TextView)  gridView.findViewById(R.id.lblhint);
             lblLetter.setText(letters[position]);
             lblHint.setText(services[position]);
         }
         else{
             gridView=convertView;
         }
        return gridView;
    }
}
