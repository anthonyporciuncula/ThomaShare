package com.android.hood.thomashare.Adapters;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.hood.thomashare.Bean.FileList;
import com.android.hood.thomashare.R;

import java.util.List;

public class FileListAdapter extends ArrayAdapter<FileList> {

    private List<FileList> fileLists;
    int layout;
//    public FileListAdapter(Context context, int resource, List<String> files) {
    public FileListAdapter(Context context, int resource, List<FileList> fileLists) {
        super(context,resource,fileLists);
        this.layout = resource;
    }

//    @Override
//    public int getCount() {
//        return myList.size();
//    }
//
//    @Override
//    public FileList getItem(int position) {
//        return myList.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return 0;
//    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder mainViewHolder = null;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(layout, parent, false);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.filename = (TextView) convertView.findViewById(R.id.tv_discover_filename);
            viewHolder.download = (ImageButton) convertView.findViewById(R.id.btn_download);
            convertView.setTag(viewHolder);
        } else {
//            mainViewHolder = (ViewHolder) convertView.getTag();
//            mainViewHolder.filename.setText(getItem(position));

            FileList fl = fileLists.get(position);
            mainViewHolder.filename.setText(fl.getDisplayName());
        }

        return convertView;
//        ListViewHolder view = (ListViewHolder) convertView;
//        if (view == null) {
//            view = new ListViewHolder(context);
//        }
//        FileList log = getItem(position);
//        view.setLog(log);
//        return view;
    }

    public class ViewHolder {
        TextView filename;
        ImageButton download;
    }
}