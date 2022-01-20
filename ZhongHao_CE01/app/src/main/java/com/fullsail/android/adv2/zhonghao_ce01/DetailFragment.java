package com.fullsail.android.adv2.zhonghao_ce01;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.exifinterface.media.ExifInterface;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.IOException;


public class DetailFragment extends Fragment {

    private static final String TAG_TITLE = "Title.TAG";
    private static final String TAG_DESCRIPT = "Description.TAG";
    private static final String TAG_IMAGE_PATH = "ImagePath.TAG";
    private static final String TAG_USER_COMMENT = "UserComment";
    private DeleteListener listener;
    private Uri mUri;

    public DetailFragment() { super(R.layout.fragment_detail); }

    public interface DeleteListener {
        void onDeleteMarker();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof DetailFragment.DeleteListener) {
            listener = (DetailFragment.DeleteListener) context;
        }
    }

    public static DetailFragment newInstance(String title, String description, String imagePath) {
        Bundle args = new Bundle();
        args.putString(TAG_TITLE, title);
        args.putString(TAG_DESCRIPT, description);
        args.putString(TAG_IMAGE_PATH, imagePath);

        DetailFragment fragment = new DetailFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView titleText = view.findViewById(R.id.textView_title);
        titleText.setText(requireArguments().getString(TAG_TITLE));

        TextView descriptText = view.findViewById(R.id.textView_description);
        descriptText.setText(requireArguments().getString(TAG_DESCRIPT));

        mUri = Uri.parse(requireArguments().getString(TAG_IMAGE_PATH));
        Bitmap bitmap = BitmapFactory.decodeFile(requireArguments().getString(TAG_IMAGE_PATH));
        ImageView detailImage = view.findViewById(R.id.imageView_detail);
        detailImage.setImageBitmap(bitmap);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_detail, menu);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        File imageReference = new File(mUri.getPath());
        try {
            crossMetadata(imageReference);
        } catch (IOException e) {
            e.printStackTrace();
        }

        listener.onDeleteMarker();
        return super.onOptionsItemSelected(item);
    }

    private void crossMetadata(File imageFile) throws IOException {
        ExifInterface exif = new ExifInterface(imageFile);

        exif.setAttribute(TAG_USER_COMMENT, "Deleted");
        exif.saveAttributes();
    }
}
