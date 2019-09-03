package testBean;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.evisible.os.controlcenter.base.JAXBGenerator;

public class JaxbTest3 {
	@Test  
    public void showMarshaller() {  
        Country country = new Country();  
        country.setName("中国");  
  
        List<Province> list = new ArrayList<Province>();  
        Province province = new Province();  
        province.setName("江苏省");  
        province.setProvCity("南京市");  
        Province province2 = new Province();  
        province2.setName("浙江省");  
        province2.setProvCity("杭州市");  
        list.add(province);  
        list.add(province2);  
  
        country.setProvinceList(list);  
  
        String str = JAXBGenerator.JaxbUtil.convertToXml(country);  
        System.out.println(str);  
    }  
  
    /** 
     *  
     */  
    @Test  
    public void showUnMarshaller() {  
        String str = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"+  
            "<country>"+  
            "    <country_name>中国</country_name>"+  
            "    <provinces>"+  
            "        <province>"+  
            "            <province_name>江苏省</province_name>"+  
             "           <prov_city>南京市</prov_city>"+  
            "        </province>"+  
             "       <province>"+  
             "           <province_name>浙江省</province_name>"+  
             "           <prov_city>杭州市</prov_city>"+  
             "       </province>"+  
            "    </provinces>"+  
            "</country>";  
        Country country = JAXBGenerator.JaxbUtil.converyToJavaBean(str, Country.class);  
        System.out.println(country);  
    }  
}
