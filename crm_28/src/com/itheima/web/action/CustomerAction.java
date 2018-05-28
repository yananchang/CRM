package com.itheima.web.action;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.itheima.domain.Customer;
import com.itheima.domain.Dict;
import com.itheima.domain.PageBean;
import com.itheima.service.CustomerService;
import com.itheima.utils.UploadUtils;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.util.ValueStack;

/**
 * 客户的控制层
 * @author Administrator
 */
public class CustomerAction extends ActionSupport implements ModelDriven<Customer>{
	
	private static final long serialVersionUID = 113695314694166436L;
	
	// 不要忘记手动new
	private Customer customer = new Customer();
	public Customer getModel() {
		return customer;
	}
	
	// 提供service的成员属性，提供set方法
	private CustomerService customerService;
	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}
	
	
	
	// 属性驱动的方式
	// 当前页，默认值就是1  
	private Integer pageCode = 1;
	public void setPageCode(Integer pageCode) {
		if(pageCode == null){
			pageCode = 1;
		}
		this.pageCode = pageCode;
	}
	
	// 每页显示的数据的条数
	private Integer pageSize = 2;
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	
	/**
	 * 分页的查询方法
	 * @return
	 */
	public String findByPage(){
		// 调用service业务层
		DetachedCriteria criteria = DetachedCriteria.forClass(Customer.class);
		//拼接查询的条件
		String cust_name = customer.getCust_name();
		if(cust_name != null && !cust_name.trim().isEmpty()) {
			//说明客户的名称输入值了
			criteria.add(Restrictions.like("cust_name", "%"+cust_name+"%"));
		}
		
		//拼接客户的级别
		Dict level = customer.getLevel();
		if(level != null && !level.getDict_id().trim().isEmpty()) {
			//说明,客户的级别肯定选择了一个级别
			criteria.add(Restrictions.eq("level.dict_id", level.getDict_id())); 
		}
		
		//客户的来源
		Dict source = customer.getSource();
		if(source != null && !source.getDict_id().trim().isEmpty()) {
			//说明,客户的级别肯定选择了一个级别
			criteria.add(Restrictions.eq("source.dict_id", source.getDict_id())); 
		}
		
		// 查询
		PageBean<Customer> page = customerService.findByPage(pageCode,pageSize,criteria);
		// 压栈
		ValueStack vs = ActionContext.getContext().getValueStack();
		// 栈顶是map<"page",page对象>
		vs.set("page", page);
		return "page";
	}
	
	/**
	 * 初始化到添加的页面
	 * @return
	 */
	public String initAddUI() {
		
		return "initAddUI";
	}
	
	/**
	 * 文件的上传, 需要在CustomerAction类中定义成员的属性,命名是有规则的!!!
	 * 要定义三个属性:
	 * private File upload(必须叫上传的元素名称)  //表示要上传的文件
	 * private String uploadFileName(以上的名称+上传文件的名称)    //表示是上传文件的名称(没有中文乱码)
	 * private String uploadContentType          //表示上传文件的MIME类型
	 * 提供set方法, 拦截器就注入值了
	 */
	
	//要上传的文件
	private File upload;
	//文件的名称
	private String uploadFileName;
	//文件的MIME类型
	private String uploadContentType;
	
	public void setUpload(File upload) {
		this.upload = upload;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

	
	/**
	 * 保存客户的方法
	 * @return
	 * @throws IOException 
	 */
	public String save() throws IOException{
		//做文件的上传
		if(uploadFileName != null) {
			//打印 略
			
			//把文件的名称处理一下
			String uuidname = UploadUtils.getUUIDName(uploadFileName);
			//把文件上传到 C:\\tomcat7\\webapps\\upload
			String path = "C:\\tomcat7\\webapps\\upload\\";
			//创建file对象
			File file = new File(path + uuidname);
			//简单方式
			FileUtils.copyFile(upload, file);
			
			//把上传的文件的路径,保存到客户表中
			customer.setFilepath(path + uuidname);
		}
		
		//保存客户成功了
		customerService.save(customer);
		return "save";
	}
	
	/**
	 * 删除客户
	 */
	public String delete() {
		//删除客户, 获取到客户的信息, 上传文件的路径
		customer = customerService.findById(customer.getCust_id());
		// 获取上传文件的路径
		String filepath = customer.getFilepath();
		//删除客户
		customerService.delete(customer);
		
		//再删除文件
		File file = new File(filepath);
		if(file.exists()) {
			file.delete();
		}
		
		return "delete";
	}
	
	
	
}












