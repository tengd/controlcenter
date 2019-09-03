package com.evisible.os.controlcenter.util.aes;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/*******************************************************************************  
 * AES加解密算法，使用byte2hex做转码 
 *   
 * @author arix04  
 *   
 */  
public class AES {
	// 加密   
    public static String Encrypt(String sSrc, String sKey) throws Exception {   
        if (sKey == null) {   
            System.out.print("Key为空null");   
            return null;   
        }   
        // 判断Key是否为16位   
        if (sKey.length() != 16) {   
            System.out.print("Key长度不是16位");   
            return null;   
        }   
        byte[] raw = sKey.getBytes();   
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");   
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");    
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);   
        byte[] encrypted = cipher.doFinal(sSrc.getBytes());   
  
        return byte2hex(encrypted).toLowerCase();   
    }   
  
    // 解密   
    public static String Decrypt(String sSrc, String sKey) throws Exception {   
        try {   
            // 判断Key是否正确   
            if (sKey == null) {   
                System.out.print("Key为空null");   
                return null;   
            }   
            // 判断Key是否为16位   
            if (sKey.length() != 16) {   
                System.out.print("Key长度不是16位");   
                return null;   
            }   
            byte[] raw = sKey.getBytes("ASCII");   
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");   
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");   
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);   
            byte[] encrypted1 = hex2byte(sSrc);   
            try {   
                byte[] original = cipher.doFinal(encrypted1);   
                String originalString = new String(original);   
                return originalString;   
            } catch (Exception e) {   
                System.out.println(e.toString());   
                return null;   
            }   
        } catch (Exception ex) {   
            System.out.println(ex.toString());   
            return null;   
        }   
    }   
  
    public static byte[] hex2byte(String strhex) {   
        if (strhex == null) {   
            return null;   
        }   
        int l = strhex.length();   
        if (l % 2 == 1) {   
            return null;   
        }   
        byte[] b = new byte[l / 2];   
        for (int i = 0; i != l / 2; i++) {   
            b[i] = (byte) Integer.parseInt(strhex.substring(i * 2, i * 2 + 2),   
                    16);   
        }   
        return b;   
    }   
  
    public static String byte2hex(byte[] b) {   
        String hs = "";   
        String stmp = "";   
        for (int n = 0; n < b.length; n++) {   
            stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));   
            if (stmp.length() == 1) {   
                hs = hs + "0" + stmp;   
            } else {   
                hs = hs + stmp;   
            }   
        }   
        return hs.toUpperCase();   
    }   
  
    public static void main(String[] args) throws Exception {   
        /*  
         * 加密用的Key 可以用26个字母和数字组成，最好不要用保留字符，虽然不会错，至于怎么裁决，个人看情况而定  
         */  
        String cKey = "1234567890123456";   
        // 需要加密的字串   
		String cSrc = "算法比较分析自DES算法1977年首次公诸于世以来,学术界对其进行了深入的研究,围绕它的安全性等方面展开了激烈的争论。在技术上,对DES的批评主要集中在以下几个方面:( 1) 作为分组密码,DES的加密单位仅有64位二进制,这对于数据传输来说太小,因为每个分组仅含8个字符,而且其中某些位还要用于奇偶校验或其他通讯开销。( 2) DES的密钥的位数太短,只有56比特,而且各次迭代中使用的密钥是递推产生的,这种相关必然降低密码体制的安全性, 在现有技术下用穷举法寻找密钥已趋于可行。(3) DES不能对抗差分和线性密码分析。迄今为止, DES算法中的S盒8个选择函数矩阵的设计原理因美国政府方面的干预, 不予公布。从这一方面严格地讲DES算法并不是一个真正的公开加密算法。S盒设计中利用了重复因子, 致使加密或解密变换的密钥具有多值性, 造成使用DES合法用户的不安全性。而且, 在DES加密算法的所有部件中, S盒是唯一的具有差分扩散功能的部件(相对于逐位异或), 其它都是简单的位置交换, 添加或删减等功能, 毫无差分扩散能力。这样, DES的安全性几乎全部依赖于S盒,攻击者只要集中力量对付S盒就行了。( 4) DES用户实际使用的密钥长度为56bit, 理论上最大加密强度为256。DES算法要提高加密强度(例如增加密钥长度), 则系统开销呈指数增长。除采用提高硬件功能和增加并行处理功能外,从算法本身和软件技术方面无法提高DES算法的加密强度。相对DES算法来说,AES算法无疑解决了上述问题,主要表现在如下几方面:( 1) 运算速度快,在有反馈模式、无反馈模式的软硬件中,Rijndael都表现出非常好的性能。( 2) 对内存的需求非常低,适合于受限环境。( 3) Rijndael是一个分组迭代密码,分组长度和密钥长度设计灵活。( 4) AES标准支持可变分组长度,分组长度可设定为32比特的任意倍数,最小值为128比特,最大值为256比特。( 5) AES的密钥长度比DES大,它也可设定为32比特的任意倍数,最小值为128比特,最大值为256比特, 所以用穷举法是不可能破解的。在可预计的将来,如果计算机的运行速度没有根本性的提高,用穷举法破解AES密钥几乎不可能。( 6) AES算法的设计策略是宽轨迹策略(Wide Trail Strategy, WTS)。WTS是针对差分分析和线性分析提出的,可对抗差分密码分析和线性密码分析。总之,Rijndael算法汇聚了安全性、效率高、易实现性和灵活性等优点,是一种较DES更好的算法。  结论经过对DES 算法和AES 算法的比较分析,我们可以得出结论, 后者的效率明显高于前者, 而且由于AES 算法的简洁性,使得它的实现更为容易。AES 作为新一代的数据加密标准, 其安全性也远远高于DES 算法。更为重要的是,AES 算法硬件实现的速度大约是软件实现的3 倍, 这就给用硬件实现加密提供了很好的机会。";
		cSrc += "什么是最安全的加密技术？是DES，SHA1，RSA，AES，DESX，RC4，MD5-RSA，SHA1-DSA还是X.509 证书？你上面给定的所有算法不是都和加密技术有关的，下面我对每一个分别进行简要的介绍：数据加密标准（DES）是一个古老的对称密钥加密算法，目前已经不再使用。它不是一个很安全的算法三重DES（Triple-DES）仍然是很安全的，但是也只是在别无他法的情况下的一个较好的选择。显然高级加密标准（AES）是一个更好的加密算法，NIST用AES代替Triple-DES作为他们的标准（下面有更详细的讨论）。其他较好的算法包括另外两个AES的变种算法Twofish和Serpent－也称为CAST-128，它是效率和安全的完美结合。这几个算法不仅比DES更安全，而且也比DES的速度更快。为什么要使用一些又慢又不安全的算法呢？HA1是一个哈希函数，而不是一个加密函数。作为一个哈希函数，SHA1还是相当优秀的，但是还需要几年的发展才能用作加密算法。如果你正在设计一个新系统，那么谨记你可能会在若干年后用SHA1代替目前的算法。我再重复一遍：只是可能RSA是一个公开密钥加密算法。RSA的密钥长度一般为2048-4096位。如果你现在的系统使用的是1024位的公开密钥，也没有必要担心，但是你可以加长密钥长度来达到更好的加密效果。高级加密标准（AES）是一个用来代替数据加密标准（DES）的算法。目前使用的一般为128,196和256位密钥，这三种密钥都是相当安全的。而且美国政府也是这样认为的。他们批准将128位密钥的AES算法用于一般数据加密，196位和256位密钥的AES算法用于秘密数据和绝密数据的加密。DESX是DES的一个改进版本。DESX的原理是利用一个随机的二进制数与加密前的数据以及解密后的数据异或。虽然也有人批评这种算法，但是与DES相比DESX确实更安全，不过DESX在许多情况下并不适用。我曾经处理过一个硬件支持DES的系统，由于有些环节不能容忍三重DES的慢速，我们在这些地方使用了DESX来代替DES。然而，这是一个非常特殊的情况。如果你需要使用DESX，理由显而易见（可能和我不得不使用DESX的原因类似）。但我建议你使用AES或者上面我提到的一些算法。RC4是一种常用于SSL连接的数据流加密算法。它已经出现很多年了，而且有很多已知和可能的缺陷，因此在一些新的工程中不要使用它。如果你目前正在使用它而且可以轻易的卸载它，那么情况也不是很坏。不过，我怀疑如果你现在正在使用它，你不可能轻易的卸载它。如果不能将它从系统中轻易的卸载，那么你还是考虑今后怎样升级它，但是不要感到很惊慌。我不会拒绝在一个使用RC4算法来加密SSL连接的网站购买东西，但是如果我现在要新建一个系统，那么我会考虑使用其他的算法，例如：AES。我认为你谈到下面两个算法MD5-RSA和SHA1-DSA的时候，你知道他们是用于数字签名的。但是不要使用MD5，因为它有很多缺陷。很多年前大家就知道MD5中存在漏洞，不过直到今年夏天才破解出来。如果你想了解关于MD5的详细信息，那你可以看看我以前写的一篇文章。你可以将SHA1和RSA或DSA配合在一起使用。目前DSA的密钥位数高达1024位，这个密钥位数已经足够长了，因此不需要担心安全问题。然而，如果NIST实现了更长的密钥位数当然更好。X.509证书是一个数据结构，常用于规定比特和字节的顺序，它本身不是一个密码系统。它通常包含一个RSA密钥，也可能包含一个DSA密钥。但是X.509证书内部以及证书本身并不是加密技术。";
		// System.out.println(cSrc);   
        // 加密   
        long lStart = System.currentTimeMillis();   
        String enString = AES.Encrypt(cSrc, cKey);   
        System.out.println("加密后的字串是：" + enString);   
		System.out.println("加密后长度：" + enString.length());
  
        long lUseTime = System.currentTimeMillis() - lStart;   
        System.out.println("加密耗时：" + lUseTime + "毫秒");   
        // 解密   
        lStart = System.currentTimeMillis();   
        String DeString = AES.Decrypt(enString, cKey);   
        System.out.println("解密后的字串是：" + DeString);   
        lUseTime = System.currentTimeMillis() - lStart;   
        System.out.println("解密耗时：" + lUseTime + "毫秒");   
    }   
}
