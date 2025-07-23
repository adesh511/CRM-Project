package in.sp.main.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import in.sp.main.api.OrdersApi;
import in.sp.main.dto.PurchasedCourse;
import in.sp.main.entities.Course;
import in.sp.main.entities.User;
import in.sp.main.repositories.CourseRepository;
import in.sp.main.repositories.OrderRepository;
import in.sp.main.repositories.UserRepository;
import in.sp.main.services.CourseService;
import in.sp.main.services.UserService;
import jakarta.validation.Valid;

@Controller
@SessionAttributes("sessionUser")
public class UserController
{

    private final OrdersApi ordersApi;	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CourseService courseService;


    UserController(OrdersApi ordersApi) {
        this.ordersApi = ordersApi;
    }
	
	
		@GetMapping({"/", "/index"})
		public String  openIndexPage(Model model)
		{
			List<Course> coursesList = courseService.getAllCourseDetails();		
			model.addAttribute("coursesList", coursesList);
			return "index";
		}
		
//		---------------------- register starts ---------------------------------
		
		@GetMapping("/register")
		public String openRegisterPage(Model model )
		{
			model.addAttribute("user", new User());
			return "register";
		}
		
		@PostMapping("/regForm")
		public String handleRegForm(@Valid @ModelAttribute("user") User user, BindingResult result, Model model)
		{
			if(result.hasErrors())
			{
				return "register";
			}
			else
			{
				try
				{
					userService.registerUserService(user);
					model.addAttribute("successMsg","Registered Successfully");	
//					return "redirect:/register";  // redirected to '/register'				
					return "register";
				}
				catch(Exception e)
				{
					e.printStackTrace();
					model.addAttribute("erroMsg","Registration failed...");
					return "error";
				}
			
			}
	
		}
		
//		----------------------register ends ---------------------------------
		
		
//		---------------------- login starts ---------------------------------
		@GetMapping("/login")
		public String openLoginPage(Model model)
		{
			model.addAttribute("user", new User());
			return "login";
		}
		
		@PostMapping("/loginForm")
		public String handleLoginForm(@ModelAttribute("user") User user, Model model)
		{
			boolean isAuthenticated = userService.loginUserService(user.getEmail(), user.getPassword());
			if(isAuthenticated)	
			{
				User authenticatedUser = userRepository.findByEmail(user.getEmail());
				
				model.addAttribute("sessionUser", authenticatedUser);
				return "user-profile";
			}	
			else
			{
				model.addAttribute("errorMsg","Incorrect Email id or Password...");
				return "login";
			}
		}
//		---------------------- login finished ---------------------------------
		
		
//		---------------------- logout starts ---------------------------------
		@GetMapping("/logout")
		public String logout(SessionStatus sessionStatus)
		{
			sessionStatus.setComplete();
			return "login";
		}
//		---------------------- logout starts ---------------------------------
		
//		---------------------- my-courses starts ---------------------------------
		
		@GetMapping("/userProfile")
		public String openUserProfile()
		{
			return "user-profile";
		}
		
		@Autowired
		private OrderRepository ordersRepository;
		
		@GetMapping("/myCourses")
		public String myCoursesPage(@ModelAttribute("sessionUser") User sessionUser, Model model)
		{			
			//puchased course databse list
			List<Object[]> pcDbList = ordersRepository.findPurchasedCoursesByEmail(sessionUser.getEmail());
				
			// from dto package
			List<PurchasedCourse> purchasedCoursesList = new ArrayList<>();
			
			for(Object[] course: pcDbList)
			{
//				System.out.println(course[0]);
//				System.out.println(course[1]);
//				System.out.println(course[2]);
//				System.out.println(course[3]);
//				System.out.println(course[4]);
				
				PurchasedCourse PurchasedCourse = new PurchasedCourse();
				
				PurchasedCourse.setPurchasedOn((String)course[0]);
				PurchasedCourse.setDescription((String)course[1]);
				PurchasedCourse.setImageUrl((String)course[2]);
				PurchasedCourse.setCourseName((String)course[3]);
				PurchasedCourse.setUpdatedOn((String)course[4]);
				
				purchasedCoursesList.add(PurchasedCourse);
				
			}
			
			model.addAttribute("purchasedCoursesList",purchasedCoursesList);
			
			return "my-courses";
		}
//		---------------------- my-courses finished ---------------------------------

}
