package com.fullsail.android.adv2.zhonghao_ce01;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;

import androidx.core.content.FileProvider;
import androidx.exifinterface.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.model.LatLng;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class FormFragment extends Fragment {

    private static final String TAG_LATLNG = "LatLng_Key.TAG";
    private static final String TAG_TITLE = "Title.TAG";
    private static final String TAG_DESCRIPT = "Description.TAG";
    EditText inputTitle;
    EditText inputDescription;
    ImageView markerImage;
    private Bitmap mBitmap;
    private LatLng mLocation;
    private File mImageFileReference;
    private SaveListener listener;

    public FormFragment() { super(R.layout.fragment_form); }

    public interface SaveListener {
        void onSaveForm();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof SaveListener) {
            listener = (SaveListener) context;
        }
    }

    public static FormFragment newInstance(LatLng location) {
        Bundle args = new Bundle();
        args.putParcelable(TAG_LATLNG, location);

        FormFragment fragment = new FormFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mLocation = requireArguments().getParcelable(TAG_LATLNG);

        inputTitle = view.findViewById(R.id.editText_title);
        inputDescription = view.findViewById(R.id.editText_description);
        markerImage = view.findViewById(R.id.imageView);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_form, menu);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.camera && getActivity() != null) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            try {
                mImageFileReference = FileUtility.createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Uri imageURI = FileProvider.getUriForFile(getActivity(),
                    "com.fullsail.android.adv2.zhonghao_ce01", mImageFileReference);

            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageURI);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

            ActivityCompat.startActivityForResult(getActivity(), intent, 0, null);
        }

        if (item.getItemId() == R.id.save && validateInput()) {
            String title = inputTitle.getText().toString();
            String description = inputDescription.getText().toString();

            try {
                saveImageMetadata(mImageFileReference, title, description);
            } catch (IOException e) {
                e.printStackTrace();
            }
            galleryAddPic();
            listener.onSaveForm();
        }

        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (getActivity() != null) {
            Uri imageURI = FileProvider.getUriForFile(getActivity(),
                    "com.fullsail.android.adv2.zhonghao_ce01", mImageFileReference);
            markerImage.setImageURI(imageURI);

            BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
            mBitmap = BitmapFactory.decodeFile(mImageFileReference.getAbsolutePath(),
                    bitmapOptions);
        }
    }

    private void saveImageMetadata(File imageFile, String title, String description) throws IOException {
        ExifInterface exif = new ExifInterface(imageFile);

        exif.setLatLong(mLocation.latitude, mLocation.longitude);
        exif.setAttribute(TAG_TITLE, title);
        exif.setAttribute(TAG_DESCRIPT, description);
        exif.saveAttributes();

        OutputStream os = new FileOutputStream(imageFile);
        mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
        os.flush();
        os.close();
    }

    private void galleryAddPic() {
        if (getActivity() != null) {
            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            File f = new File(mImageFileReference.getAbsolutePath());
            Uri contentUri = Uri.fromFile(f);
            mediaScanIntent.setData(contentUri);
            getActivity().sendBroadcast(mediaScanIntent);
        }
    }

    private boolean validateInput() {
        String title = inputTitle.getText().toString();
        if (title.trim().isEmpty()) {
            Toast.makeText(getContext(), R.string.warning_empty, Toast.LENGTH_SHORT).show();
            return false;
        }

        String description = inputDescription.getText().toString();
        if (description.trim().isEmpty()) {
            Toast.makeText(getContext(), R.string.warning_empty, Toast.LENGTH_SHORT).show();
            return false;
        }

        Drawable image = markerImage.getDrawable();
        if (image == null || mBitmap == null) {
            Toast.makeText(getContext(), R.string.warning_empty, Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}
