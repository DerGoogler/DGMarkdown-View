package com.dergoogler.markdown;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.*;

import android.app.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import android.content.*;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.text.*;
import android.text.style.*;
import android.util.*;
import android.webkit.*;
import android.animation.*;
import android.view.animation.*;

import java.util.*;
import java.util.regex.*;
import java.text.*;

import org.json.*;

import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.EditText;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.Intent;
import android.content.ClipData;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.DialogFragment;
import androidx.core.content.ContextCompat;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;


public class MainActivity extends AppCompatActivity {
    public final int REQ_CD_PICKFILE = 101;

    private double tabs = 0;
    private String c = "";
    private double cornors = 0;
    private double stroke = 0;
    private String focus = "";
    private String pressed = "";
    private String strokecolor = "";
    private String shrap = "";
    private String load_empty_temp = "";
    private String new_user = "";

    private LinearLayout linear2;
    private LinearLayout background;
    private LinearLayout bottombar;
    private LinearLayout linear3;
    private TextView textview1;
    private ImageView imageview1;
    private LinearLayout editbackgroud;
    private LinearLayout markdownbackground;
    private EditText edittext1;
    private MarkdownView markdownview1;

    private SharedPreferences savedmd;
    private Intent pickfile = new Intent(Intent.ACTION_GET_CONTENT);

    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.main);
        initialize(_savedInstanceState);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
        } else {
            initializeLogic();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1000) {
            initializeLogic();
        }
    }

    private void initialize(Bundle _savedInstanceState) {
        linear2 = (LinearLayout) findViewById(R.id.linear2);
        background = (LinearLayout) findViewById(R.id.background);
        bottombar = (LinearLayout) findViewById(R.id.bottombar);
        linear3 = (LinearLayout) findViewById(R.id.linear3);
        textview1 = (TextView) findViewById(R.id.textview1);
        imageview1 = (ImageView) findViewById(R.id.imageview1);
        editbackgroud = (LinearLayout) findViewById(R.id.editbackgroud);
        markdownbackground = (LinearLayout) findViewById(R.id.markdownbackground);
        edittext1 = (EditText) findViewById(R.id.edittext1);
        markdownview1 = (MarkdownView) findViewById(R.id.markdownview1);
        savedmd = getSharedPreferences("temp_markdown", Activity.MODE_PRIVATE);
        pickfile.setType("text/*");
        pickfile.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);

        imageview1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                PopupMenu o = new PopupMenu(MainActivity.this, imageview1);
                Menu menu = o.getMenu();
                menu.add("Open File");
                menu.add("Save File");
                o.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getTitle().toString()) {
                            case "Open File": {
                                startActivityForResult(pickfile, REQ_CD_PICKFILE);
                                break;
                            }
                            case "Save File": {
                                final AlertDialog exity = new AlertDialog.Builder(MainActivity.this, R.style.CustomDialogTheme).create();
                                View inflate = getLayoutInflater().inflate(R.layout.save, null);
                                /*--group-"button"-begin--*/
                                /*--group-"blue"-begin--*/
                                TextView textview10 = (TextView) inflate.findViewById(R.id.textview10);
                                /*--group-"blue"-end--*/
                                /*--group-"white"-begin--*/
                                TextView textview9 = (TextView) inflate.findViewById(R.id.textview9);
                                /*--group-"white"-end--*/
                                /*--group-"button"-end--*/
                                /*--group-"dialog content"-begin--*/
                                /*--group-"title"-begin--*/
                                TextView textview7 = (TextView) inflate.findViewById(R.id.textview7);
                                /*--group-"title"-end--*/
                                /*--group-"content"-begin--*/
                                final EditText textview8 = (EditText) inflate.findViewById(R.id.textview8);
                                /*--group-"content"-end--*/
                                /*--group-"dialog content"-end--*/
                                textview7.setText("Save as file");
                                textview10.setText("Save");
                                textview9.setText("Cancel");
                                _rippleRoundStroke(textview9, "#FFFFFFFF", "#00C5FA", 15, 3, "#EEEEEE");
                                _rippleRoundStroke(textview8, "#00000000", "#00C5FA", 15, 3, "#EEEEEE");
                                _rippleRoundStroke(textview10, "#FF0076FF", "#00C5FA", 15, 0, "#EEEEEE");
                                textview10.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View _view) {
                                        if (textview8.getText().toString().equals("")) {
                                            SketchwareUtil.showMessage(getApplicationContext(), "You cannot save an file with empty name");
                                        } else {
                                            if (edittext1.getText().toString().equals("")) {
                                                SketchwareUtil.showMessage(getApplicationContext(), "You cannot save an empty markdown content");
                                            } else {
                                                FileUtil.writeFile(FileUtil.getExternalStorageDir().concat("/_DGS-DATA/MarkdownView/files/".concat(textview8.getText().toString())), edittext1.getText().toString());
                                                SketchwareUtil.showMessage(getApplicationContext(), "File saved as ".concat(textview8.getText().toString()));
                                                exity.dismiss();
                                            }
                                        }
                                    }
                                });
                                textview9.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View _view) {
                                        exity.dismiss();
                                    }
                                });
                                exity.setView(inflate);
                                exity.setCancelable(false);
                                exity.show();

                                break;
                            }
                        }
                        return (true);
                    }
                });
                o.show();
            }
        });

        edittext1.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
                final String _charSeq = _param1.toString();
                markdownview1.setMarkDownText(_charSeq);
                savedmd.edit().putString("temp", _charSeq).commit();
                FileUtil.writeFile(FileUtil.getExternalStorageDir().concat("/_DGS-DATA/MarkdownView/temp/last.md"), _charSeq);
            }

            @Override
            public void beforeTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {

            }

            @Override
            public void afterTextChanged(Editable _param1) {

            }
        });
    }

    private void initializeLogic() {
        markdownbackground.setVisibility(View.GONE);
        _styles();
        _bottombars();
        _markdownengine();
    }

    @Override
    protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
        super.onActivityResult(_requestCode, _resultCode, _data);
        switch (_requestCode) {
            case REQ_CD_PICKFILE:
                if (_resultCode == Activity.RESULT_OK) {
                    ArrayList<String> _filePath = new ArrayList<>();
                    if (_data != null) {
                        if (_data.getClipData() != null) {
                            for (int _index = 0; _index < _data.getClipData().getItemCount(); _index++) {
                                ClipData.Item _item = _data.getClipData().getItemAt(_index);
                                _filePath.add(FileUtil.convertUriToFilePath(getApplicationContext(), _item.getUri()));
                            }
                        } else {
                            _filePath.add(FileUtil.convertUriToFilePath(getApplicationContext(), _data.getData()));
                        }
                    }
                    edittext1.setText(FileUtil.readFile(_filePath.get((int) (0))));
                } else {

                }
                break;
            default:
                break;
        }
    }


    @Override
    public void onBackPressed() {
        final AlertDialog exit = new AlertDialog.Builder(MainActivity.this, R.style.CustomDialogTheme).create();
        View inflate = getLayoutInflater().inflate(R.layout.dialog, null);
        /*--group-"button"-begin--*/
        /*--group-"blue"-begin--*/
        TextView textview10 = (TextView) inflate.findViewById(R.id.textview10);
        /*--group-"blue"-end--*/
        /*--group-"white"-begin--*/
        TextView textview9 = (TextView) inflate.findViewById(R.id.textview9);
        /*--group-"white"-end--*/
        /*--group-"button"-end--*/
        /*--group-"dialog content"-begin--*/
        /*--group-"title"-begin--*/
        TextView textview7 = (TextView) inflate.findViewById(R.id.textview7);
        /*--group-"title"-end--*/
        /*--group-"content"-begin--*/
        TextView textview8 = (TextView) inflate.findViewById(R.id.textview8);
        /*--group-"content"-end--*/
        /*--group-"dialog content"-end--*/
        textview7.setText("Leave?");
        _htmlToTextView(textview8, "You want really leave this app?\n<br/>\n<em>The current text will be saved in: </em><b>/sdcard/_DGS-DATA/MarkdownView/temp/</b>", "#00C5FA");
        textview10.setText("Stay");
        textview9.setText("Leave");
        _rippleRoundStroke(textview9, "#FFFFFFFF", "#00C5FA", 15, 3, "#EEEEEE");
        _rippleRoundStroke(textview10, "#FF0076FF", "#00C5FA", 15, 0, "#EEEEEE");
        textview10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                exit.dismiss();
            }
        });
        textview9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                finishAffinity();
            }
        });
        exit.setView(inflate);
        exit.setCancelable(false);
        exit.show();
    }

    public void _styles() {
        shrap = "#";
        /*--group-""-begin--*/
        cornors = 15;
        stroke = 3;
        strokecolor = shrap.concat("EEEEEE");
        pressed = shrap.concat("00000000");
        focus = shrap.concat("00000000");
        /*--group-""-end--*/
        /*--group-""-begin--*/
        _rippleRoundStroke(edittext1, focus, pressed, cornors, stroke, strokecolor);
        /*--group-""-end--*/
    }


    public void _markdownengine() {
        if (savedmd.getString("temp", "").equals("")) {
            savedmd.edit().putString("temp", "# Hello World!").commit();
            new_user = savedmd.getString("temp", "");
            markdownview1.setMarkDownText(new_user);
            edittext1.setText(savedmd.getString("temp", ""));
        } else {
            load_empty_temp = savedmd.getString("temp", "");
            markdownview1.setMarkDownText(load_empty_temp);
            edittext1.setText(savedmd.getString("temp", ""));
        }
        markdownview1.setOpenUrlInBrowser(true);
    }


    public void _bottombars() {
        com.google.android.material.bottomnavigation.BottomNavigationView btm = new com.google.android.material.bottomnavigation.BottomNavigationView(MainActivity.this);
        btm.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        bottombar.addView(btm);
        Menu menu = btm.getMenu();
        menu.add(Menu.NONE, 0, Menu.NONE, "Write").setIcon(R.drawable.round_edit_black_24dp);
        menu.add(Menu.NONE, 1, Menu.NONE, "Preview").setIcon(R.drawable.round_remove_red_eye_black_24dp);
        btm.setOnNavigationItemSelectedListener(new com.google.android.material.bottomnavigation.BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(final MenuItem item) {
                switch (item.getItemId()) {
                    case 0:
                        editbackgroud.setVisibility(View.VISIBLE);
                        markdownbackground.setVisibility(View.GONE);
                        break;
                    case 1:
                        markdownbackground.setVisibility(View.VISIBLE);
                        editbackgroud.setVisibility(View.GONE);
                        break;
                }
                return true;
            }
        });
    }


    public void _rippleRoundStroke(final View _view, final String _focus, final String _pressed, final double _round, final double _stroke, final String _strokeclr) {
        android.graphics.drawable.GradientDrawable GG = new android.graphics.drawable.GradientDrawable();
        GG.setColor(Color.parseColor(_focus));
        GG.setCornerRadius((float) _round);
        GG.setStroke((int) _stroke,
                Color.parseColor("#" + _strokeclr.replace("#", "")));
        android.graphics.drawable.RippleDrawable RE = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{Color.parseColor("#FF757575")}), GG, null);
        _view.setBackground(RE);
    }


    public void _htmlToTextView(final TextView _textview, final String _html, final String _linkColor) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            _textview.setText(Html.fromHtml(_html, Html.FROM_HTML_MODE_COMPACT));
        } else {
            _textview.setText(Html.fromHtml(_html));
        }

        _textview.setClickable(true);
        android.text.util.Linkify.addLinks(_textview, android.text.util.Linkify.ALL);
        if (!_linkColor.equals("")) {
            _textview.setLinkTextColor(Color.parseColor(_linkColor));
        } else {
            _textview.setLinkTextColor(Color.parseColor("#ff0000ff"));
        }

        _textview.setLinksClickable(true);

        _textview.setMovementMethod(
                android.text.method.LinkMovementMethod.getInstance());
    }


    @Deprecated
    public void showMessage(String _s) {
        Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
    }

    @Deprecated
    public int getLocationX(View _v) {
        int _location[] = new int[2];
        _v.getLocationInWindow(_location);
        return _location[0];
    }

    @Deprecated
    public int getLocationY(View _v) {
        int _location[] = new int[2];
        _v.getLocationInWindow(_location);
        return _location[1];
    }

    @Deprecated
    public int getRandom(int _min, int _max) {
        Random random = new Random();
        return random.nextInt(_max - _min + 1) + _min;
    }

    @Deprecated
    public ArrayList<Double> getCheckedItemPositionsToArray(ListView _list) {
        ArrayList<Double> _result = new ArrayList<Double>();
        SparseBooleanArray _arr = _list.getCheckedItemPositions();
        for (int _iIdx = 0; _iIdx < _arr.size(); _iIdx++) {
            if (_arr.valueAt(_iIdx))
                _result.add((double) _arr.keyAt(_iIdx));
        }
        return _result;
    }

    @Deprecated
    public float getDip(int _input) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, getResources().getDisplayMetrics());
    }

    @Deprecated
    public int getDisplayWidthPixels() {
        return getResources().getDisplayMetrics().widthPixels;
    }

    @Deprecated
    public int getDisplayHeightPixels() {
        return getResources().getDisplayMetrics().heightPixels;
    }
}
