package hfad.com.ovdinfomojetsdeaemuje;


import android.os.Bundle;
import android.app.Fragment;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class FullTextFragment extends Fragment {

    public static final String NEWS_NUMBER = "newsNumber";
    TextView textView;
    String textNews;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_full_text, container, false);

        textView = (TextView) view.findViewById(R.id.textFullNews);
        Bundle bundle = this.getArguments();
        if (bundle!=null) {
            textNews = bundle.getString(NEWS_NUMBER);
        }
        textView.setText(Html.fromHtml(textNews));
        textView.setMovementMethod(new ScrollingMovementMethod());

        return view;
    }

}
