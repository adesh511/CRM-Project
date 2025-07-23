package in.sp.main.controllers;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import in.sp.main.entities.Course;
import in.sp.main.services.CourseService;


@Controller
public class AdminController
{	
	private String UPLOAD_DIR = "src/main/resources/static/uploads/";
	private String IMAGE_URL = "http://localhost:8080/uploads/";

    private final UserController userController;	
	@Autowired
	private CourseService courseService;


    AdminController(UserController userController) {
        this.userController = userController;
    }
	
	
	@GetMapping("/adminLogin")
	public String openAdminLoginPage()
	{

		return "admin-login";
	}
	
	@PostMapping("/adminLoginForm")
	public String openadminLoginForm(@RequestParam("adminemail") String aemail, @RequestParam("adminpass") String apass, Model model)
	{
		if(aemail.equals("admin@gmail.com") && apass.equals("admin123"))
		{
			return "admin-profile";
		}
		else
		{
			model.addAttribute("errorMsg", "Invalid email or password");
			return "admin-login";
		}

	}
		@GetMapping("/adminProfile")
		public String openAdminProfilePage()
		{
			return "admin-profile";
		}
		
		@GetMapping("/courseManagement")
		public String openCourseManagementPage(Model model)
		{			
			List<Course> coursesList = courseService.getAllCourseDetails();	
			model.addAttribute("coursesList", coursesList);
			return "course-management";
		}

//		 ------------------add course start--------------------------------
		@GetMapping("/addCourse")
		public String openAddCoursePage(Model model)
		{
			model.addAttribute("course", new Course());
			
			return "add-course";
		}
		
		@PostMapping("/addCourseForm")// get course obj. through @ModelAttribute,     // get Image data
		public String handleAddCourseForm(@ModelAttribute("course") Course course, @RequestParam("courseImg") MultipartFile courseImg, Model model)
		{
			try 
			{
			courseService.addCourse(course, courseImg);
			model.addAttribute("successMsg", "Course added successfully");
			}
			catch(Exception e)
			{
				e.printStackTrace();
				model.addAttribute("errorMsg", "Course not added due to some error");
			}
			
			return "add-course";
		}
		
//		 ------------------add course ends--------------------------------
		
//		 ------------------edit course start--------------------------------
		@GetMapping("/editCourse")
		public String openEditCoursePage(@RequestParam("courseName") String courseName, Model model)
		{		
			Course course = courseService.getCourseDetails(courseName);	
			
			model.addAttribute("course", course);
			model.addAttribute("newCourseObj", new Course());
			
			return "edit-course";
		}
		
		@PostMapping("/updateCourseDetailsForm")
		public String updateCourseForm(@ModelAttribute("newCourseObj") Course newCourseObj, @RequestParam("courseImg") MultipartFile courseImg, RedirectAttributes redirectAttributes)
		{
			try
			{
				Course oldCourseObj = courseService.getCourseDetails(newCourseObj.getName());
				newCourseObj.setId(oldCourseObj.getId());
				
				if(!courseImg.isEmpty())				
				{
					String imgName = courseImg.getOriginalFilename(); // get image name
					Path imgPath = Paths.get(UPLOAD_DIR+imgName);  // path + file name
					Files.write(imgPath, courseImg.getBytes());
					
					String imgUrl = IMAGE_URL+imgName;
					newCourseObj.setImageUrl(imgUrl);
					
				}
				else
				{
					newCourseObj.setImageUrl(oldCourseObj.getImageUrl());
				}		
				courseService.updateCourseDetails(newCourseObj);
				redirectAttributes.addFlashAttribute("successMsg", "Course details updated successfully...");
			}
			catch (Exception e)
			{
				redirectAttributes.addFlashAttribute("errorMsg", "Course details not updated due to some error...");
				e.printStackTrace();
			}
			
			return "redirect:/courseManagement";
			
		}
//		 ------------------edit course start--------------------------------

		@GetMapping("/deleteCourseDetails")
		public String deleteCourseDetails(@RequestParam("courseName") String courseName, RedirectAttributes redirectAttributes)
		{
			try {
				courseService.deleteCourseDetails(courseName);
				redirectAttributes.addFlashAttribute("successMsg", "Course details deleted successfully...");
			} catch (Exception e) 
			{
				redirectAttributes.addFlashAttribute("errorMsg", "Course details did not delete due to some error...");
				e.printStackTrace();
			}
			return "redirect:/courseManagement";
		}
}
