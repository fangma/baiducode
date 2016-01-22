package demo.iflytek.com.study;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.iflytek.android.framework.annotation.ViewInject;
import com.iflytek.android.framework.base.BaseActivity;
import com.iflytek.android.framework.toast.BaseToast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


public class MainActivity extends BaseActivity {

    @ViewInject(id =R.id.textView,methodName = "clickDeal",listenerName = "onClick")
    private TextView textView;
    @ViewInject(id =R.id.button,methodName = "clickDeal",listenerName = "onClick")
    private Button button;

    private Button bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    public void  clickDeal(View view){
        switch (view.getId()){
            case R.id.button:
                BaseToast.showToastNotRepeat(getApplicationContext(),"111",1000);
                try {

                    DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
                    DocumentBuilder builder = builderFactory.newDocumentBuilder();
                    Document document =builder.parse(getAssets().open("testassest.xml"));
                    Element element = document.getDocumentElement();
                    NodeList nodeList = element.getElementsByTagName("lan");
                    for (int i=0 ;i<nodeList.getLength();i++){
                        Element lan = (Element) nodeList.item(i);
                        textView.append(lan.getAttribute("id"));
                        textView.append(lan.getElementsByTagName("name").item(0).getTextContent());
                        textView.append(lan.getElementsByTagName("value").item(0).getTextContent());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ParserConfigurationException e) {
                    e.printStackTrace();
                } catch (SAXException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }

}
