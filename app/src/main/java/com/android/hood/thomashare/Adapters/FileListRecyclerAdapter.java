package com.android.hood.thomashare.Adapters;


import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.hood.thomashare.Bean.FileList;
import com.android.hood.thomashare.R;

import java.util.List;

import static java.security.AccessController.getContext;

public class FileListRecyclerAdapter extends RecyclerView.Adapter<FileListRecyclerAdapter.MyViewHolder> {

//    private List<Interest> interestList;

    private List<FileList> fileLists;
    Context context;
    /**
     * View holder class
     */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView interestText;
        public ImageButton btnDownload;
        public TextView tag;
        public TextView dateTime;
        public TextView uploaderName;

        public MyViewHolder(View view) {
            super(view);
            interestText = (TextView) view.findViewById(R.id.tv_discover_filename);
            tag = (TextView) view.findViewById(R.id.tv_tag);
            uploaderName = (TextView) view.findViewById(R.id.tv_uploader);
            dateTime = (TextView) view.findViewById(R.id.tv_dateTIme);
            btnDownload = (ImageButton) view.findViewById(R.id.btn_download);
        }
    }

    public FileListRecyclerAdapter(Context context, List<FileList> fileLists) {
        this.fileLists = fileLists;
        this.context = context;
    }
//    public FileListRecyclerAdapter(List<Interest> interestList) {
//        this.fileLists = interestList;
//    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        System.out.println("Bind [" + holder + "] - Pos [" + position + "]");
//        Interest c = fileLists.get(position);
        FileList c = fileLists.get(position);
//        holder.interestText.setText(c.interest);
        holder.interestText.setText(c.getDisplayName());
        holder.tag.setText(c.getTag());
        holder.uploaderName.setText(c.getUploader());
        holder.dateTime.setText(c.getDateTime());


        final String uri = c.getDownloadUri();
        final String displayName = c.getDisplayName();
        holder.btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("URI",uri);
                download(uri,displayName);
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.d("RV", "Item size [" + fileLists.size() + "]");
        return fileLists.size();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_files, parent, false);
        return new MyViewHolder(v);
    }

    public void download(String uri, String displayName){
//        String url = "https://firebasestorage.googleapis.com/v0/b/thomashare-161ce.appspot.com/o/Dphob2mQbNVsStXHoKfJk7ybSWC2%2Ft-table.pdf?alt=media&token=c1837683-c09d-49e0-8fe6-7eb09cd76725";
        String url = uri;
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setDescription("Some Description");
        request.setTitle(displayName);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            request.allowScanningByMediaScanner();
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        }
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "t-table.pdf");

        DownloadManager manager = (DownloadManager)context.getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);
    }
}
