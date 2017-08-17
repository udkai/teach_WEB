package test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.balance.base.service.BaseTeacherInfoService;
import com.balance.base.service.BaseUserInfoService;
import com.balance.util.test.BaseTest;

public class ImportUserinfoTest extends BaseTest {
	@Autowired
	BaseUserInfoService baseUserInfoService;
	@Autowired
	BaseTeacherInfoService baseTeacherInfoService;

	@Test
	@Rollback(false)
	public void testImport() {
		// String string = baseUserInfoService.saveImport("D:\\userinfo.xls",
		// "123");
		// System.out.println(string);
		String string = baseTeacherInfoService.saveImport("D:\\teacherinfo.xls", "123");
		System.out.println(string);
	}
}
