package utility.injectors;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.hood.thomashare.Bean.FileList;
import com.android.hood.thomashare.R;

import java.io.File;


public class ListViewHolder extends LinearLayout {
    Context mContext;
    FileList mLog;

    public ListViewHolder(Context context) {
        super(context);
        mContext = context;
        setup();
    }

    public ListViewHolder(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        setup();
    }

    private void setup() {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.list_row_layout, this);
    }

    public void setLog(FileList log) {
        mLog = log;
        TextView tvTitle = (TextView) findViewById(R.id.txtTitle);
        tvTitle.setText(mLog.getTitle() + "");
        TextView tvDescription = (TextView) findViewById(R.id.txtDescription);
        tvDescription.setText(mLog.getDescription() + "");
    }
}