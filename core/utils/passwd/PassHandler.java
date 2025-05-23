package com.yf.exam.core.utils.passwd;


import com.yf.exam.core.utils.file.Md5Util;
import org.apache.commons.lang3.RandomStringUtils;

/**
 * 通用的密码处理类，用于生成密码和校验密码
 * ClassName: PassGenerator <br/>
 * @version
 */
public class PassHandler {

	/**
	 * checkPass:校验密码是否一致
	 * @param inputPass 用户传入的密码
	 * @param salt 数据库保存的密码随机码
	 * @param pass 数据库保存的密码MD5
	 * @return
	 */
	public static boolean checkPass(String inputPass , String salt , String pass){
		String pwdMd5 = Md5Util.md5(inputPass);
		return Md5Util.md5(pwdMd5 + salt).equals(pass);
	}


	/**
	 *
	 * buildPassword:用于用户注册时产生一个密码
	 * @param inputPass 输入的密码
	 * @return PassInfo 返回一个密码对象，记得保存
	 */
	public static PassInfo buildPassword(String inputPass) {

		//产生一个6位数的随机码
		String salt = RandomStringUtils.randomAlphabetic(6);
		//加密后的密码
		String encryptPassword = Md5Util.md5(Md5Util.md5(inputPass)+salt);
		//返回对象
		return new PassInfo(salt,encryptPassword);
	}


	public static void main(String[] args) {

		PassInfo info = buildPassword("190601");

		System.out.println(info.getPassword());
		System.out.println(info.getSalt());
	}

}
