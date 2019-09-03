package com.evisible.os.controlcenter.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.text.MessageFormat;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;


/**
 * <p>JAXBjavabean转换成xml,xml转换成javabean</p>
 * @author TengDong
 * @date 20160425
 */
public class JAXBGenerator {
			/**
			 *@see 读取xml文件
			 */
			public static class JaxbReadXml{
				@SuppressWarnings("unchecked")
			    public static <T> T readString(Class<T> clazz, String context) throws JAXBException {
			        try {
			            JAXBContext jc = JAXBContext.newInstance(clazz);
			            Unmarshaller u = jc.createUnmarshaller();
			            return (T) u.unmarshal(new File(context));
			        } catch (JAXBException e) {
			            // logger.trace(e);
			            throw e;
			        }
			    }

			    @SuppressWarnings("unchecked")
			    public static <T> T readConfig(Class<T> clazz, String config, Object[] arguments) throws IOException,
			            JAXBException {
			        InputStream is = null;
			        try {
			            if (arguments.length > 0) {
			                config = MessageFormat.format(config, arguments);
			            }
			            // logger.trace("read configFileName=" + config);
			            JAXBContext jc = JAXBContext.newInstance(clazz);
			            Unmarshaller u = jc.createUnmarshaller();
			            is = new FileInputStream(config);
			            return (T) u.unmarshal(is);
			        } catch (IOException e) {
			            // logger.trace(config, e);
			            throw e;
			        } catch (JAXBException e) {
			            // logger.trace(config, e);
			            throw e;
			        } finally {
			            if (is != null) {
			                is.close();
			            }
			        }
			    }

			    @SuppressWarnings("unchecked")
			    public static <T> T readConfigFromStream(Class<T> clazz, InputStream dataStream) throws JAXBException {
			        try {
			            JAXBContext jc = JAXBContext.newInstance(clazz);
			            Unmarshaller u = jc.createUnmarshaller();
			            return (T) u.unmarshal(dataStream);
			        } catch (JAXBException e) {
			            // logger.trace(e);
			            throw e;
			        }
			    }

			}
			
			/**
			 * @see Jaxb2工具类
			 * @see Marshaller.JAXB_FORMATTED_OUTPUT 决定是否在转换成xml时同时进行格式化（即按标签自动换行，否则即是一行的xml）
			 * @see Marshaller.JAXB_ENCODING xml的编码方式,另外，Marshaller 还有其他Property可以设置，可以去查阅api。
			 */
			public static class JaxbUtil{
				/** 
			     * JavaBean转换成xml 
			     * 默认编码UTF-8 
			     * @param obj 
			     * @param writer 
			     * @return  
			     */  
			    public static String convertToXml(Object obj) {  
			        return convertToXml(obj, "UTF-8");  
			    }  
			  
			    /** 
			     * JavaBean转换成xml 
			     * @param obj 
			     * @param encoding  
			     * @return  
			     */  
			    public static String convertToXml(Object obj, String encoding) {  
			        String result = null;  
			        try {  
			            JAXBContext context = JAXBContext.newInstance(obj.getClass());  
			            Marshaller marshaller = context.createMarshaller();  
			            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);  
			            marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);  
			  
			            StringWriter writer = new StringWriter();  
			            marshaller.marshal(obj, writer);  
			            result = writer.toString();  
			        } catch (Exception e) {  
			            e.printStackTrace();  
			        }  
			  
			        return result;  
			    }  
			  
			    /** 
			     * xml转换成JavaBean 
			     * @param xml 
			     * @param c 
			     * @return 
			     */  
			    @SuppressWarnings("unchecked")  
			    public static <T> T converyToJavaBean(String xml, Class<T> c) {  
			        T t = null;  
			        try {  
			            JAXBContext context = JAXBContext.newInstance(c);  
			            Unmarshaller unmarshaller = context.createUnmarshaller();  
			            t = (T) unmarshaller.unmarshal(new StringReader(xml));  
			        } catch (Exception e) {  
			            e.printStackTrace();  
			        }  
			  
			        return t;  
			    }  
			}
			
			
			
			
}
