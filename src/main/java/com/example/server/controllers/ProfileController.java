package com.example.server.controllers;


import com.example.server.payload.request.profile.ContactInfoDto;
import com.example.server.payload.request.profile.EducationRequestDto;
import com.example.server.payload.request.profile.SocialRequestDto;
import com.example.server.payload.response.ResponseHandler;
import com.example.server.services.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)

public class ProfileController {

    private final ProfileService profileService;

    @PostMapping("/upload-image")
    public ResponseEntity<?> uploadImage(HttpServletRequest httpServletRequest,
                                         @RequestParam("image")MultipartFile image
    ){
        String imageUloaded = profileService.uploadImage(httpServletRequest,image);
        return ResponseHandler.generateResponse(
                imageUloaded!=null ? "image Uploaded successfully" : "error while uploading image",
                imageUloaded!=null ? HttpStatus.OK : HttpStatus.BAD_REQUEST,
                imageUloaded
        );
    }

    @PostMapping("/upload-coverimage")
    public ResponseEntity<?> uploadCoverImage(HttpServletRequest httpServletRequest,
                                         @RequestParam("coverimage")MultipartFile image
    ){
        String imageUloaded = profileService.uploadCoverImage(httpServletRequest,image);
        return ResponseHandler.generateResponse(
                imageUloaded !=null ? "cover image Uploaded successfully" : "error while uploading cover image",
                imageUloaded !=null ? HttpStatus.OK : HttpStatus.BAD_REQUEST,
                imageUloaded
        );
    }


    @PutMapping("/update/education")
    public ResponseEntity<?> updateEducation(HttpServletRequest httpServletRequest,
                                    @RequestBody EducationRequestDto education
    ){
        return ResponseHandler.generateResponse("education updated successfully",
                HttpStatus.OK,
                profileService.updateEducation(httpServletRequest,education)
        );
    }

    @PutMapping("/update/about")
    public ResponseEntity<?> updateAbout(HttpServletRequest httpServletRequest,
                                    @RequestParam String about
    ){
        return ResponseHandler.generateResponse("about user updated successfully",
                HttpStatus.OK,
                profileService.updateAbout(httpServletRequest,about)
        );
    }

    @PutMapping("/update/bio")
    public ResponseEntity<?> updateBio(HttpServletRequest httpServletRequest,
                                    @RequestParam String bio
    ){
        return ResponseHandler.generateResponse("bio updated successfully",
                HttpStatus.OK,
                profileService.updateBio(httpServletRequest,bio)
        );
    }
    @PutMapping("/update/skills")
    public ResponseEntity<?> updateSkills(HttpServletRequest httpServletRequest,
                                       @RequestParam  String skills
    ){
        //System.out.println("siklls: "+ Arrays.toString(skills));
        return ResponseHandler.generateResponse("skills updated successfully",
                HttpStatus.OK,
                profileService.updateSkills(httpServletRequest,skills)
        );
    }

    @GetMapping("/{userid}")
    public ResponseEntity<?> getProfile(HttpServletRequest req, @PathVariable("userid") Long userId){
        return ResponseHandler.generateResponse("Profile get successfully",
                HttpStatus.OK,
                profileService.getProfileDto(req,userId)
        );
    }

    @GetMapping("/follow/{user_id}")
    public ResponseEntity<?> followUser(HttpServletRequest servletRequest,@PathVariable("user_id") Long userId){
        String follow = profileService.follow(servletRequest,userId);
        return ResponseHandler.generateResponse(
                "user with id : "+userId+" , has been "+follow+" successfully",
                HttpStatus.OK,
                null
        );
    }

    @GetMapping("/followers/get/{user_id}")
    public ResponseEntity<?> getFollowers(@PathVariable("user_id") Long userId){
        return ResponseHandler.generateResponse("followers get successfully",
                HttpStatus.OK,
                profileService.getFollowers(userId));

    }

    @GetMapping("/following/get/{user_id}")
    public ResponseEntity<?> getFollowing(@PathVariable("user_id") Long userId){
        return ResponseHandler.generateResponse("following get successfully",
                HttpStatus.OK,
                profileService.getFollowing(userId));

    }
    @GetMapping("/following-following/get/{user_id}")
    public ResponseEntity<?> getFollowersAndFollowing(@PathVariable("user_id") Long userId){
        return ResponseHandler.generateResponse("followers-following get successfully",
                HttpStatus.OK,
                profileService.getFollowersAndFollowing(userId));

    }

    @GetMapping("/posts/all/{user_id}")
    public ResponseEntity<?>getPosts(HttpServletRequest servletRequest,
                                     @PathVariable("user_id") Long userId){
        return ResponseHandler.generateResponse("posts get successfully",
                HttpStatus.OK,
                profileService.allPosts(servletRequest,userId));
    }




    /**
     * phone controllers
     * */
    @PutMapping("/contactInfo/update")
    public ResponseEntity<?> updateContactInfo(
            HttpServletRequest req,
            @Valid @RequestBody ContactInfoDto contactDto
    ){
        return ResponseHandler.generateResponse("contact info updated successfully",
                HttpStatus.OK,
                profileService.updateContactInfo(req,contactDto)
        );
    }

    /**
     * social linkes
     * */
    @PostMapping("/social/add")
    public ResponseEntity<?> addSocial(HttpServletRequest req,@RequestBody SocialRequestDto socialDto){
        return ResponseHandler.generateResponse("social-link added successfully",
                HttpStatus.OK,
                profileService.addSocialLink(req,socialDto)
        );
    }

    @PutMapping("/social/update")
    public ResponseEntity<?> updateSocial(HttpServletRequest req,@RequestBody SocialRequestDto socialDto){
        return ResponseHandler.generateResponse("social-link updated successfully",
                HttpStatus.OK,
                profileService.updateSocialLink(req,socialDto)
        );
    }

    @DeleteMapping("/social/delete/{social_name}")
    public ResponseEntity<?> deleteSocial(HttpServletRequest req,@PathVariable("social_name") String social_name){
        return ResponseHandler.generateResponse("social-link deleted successfully",
                HttpStatus.OK,
                profileService.deleteSocial(req,social_name)
        );
    }




}
