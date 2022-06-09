package com.maktabProject.Part4MaktabFinalProject.Final.part.services;

import com.maktabProject.Part4MaktabFinalProject.Final.part.entity.Users;
import com.maktabProject.Part4MaktabFinalProject.Final.part.entity.enums.UserStatus;
import com.maktabProject.Part4MaktabFinalProject.Final.part.repository.UsersRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private JavaMailSender mailSender;


    @Transactional
    public Users saveOrUpdate(Users users) {
        users.setExpired(false);
        users.setCredentialsExpired(false);
        users.setLocked(false);
        users.setEnabled(true);
        users.setPassword(encoder.encode(users.getPassword()));
        return usersRepository.save(users);
    }


    @Transactional
    public List<Users> findAll() {
        return usersRepository.findAll();
    }


    @Transactional
    public Users findById(Long id) {
        return usersRepository.findById(id).orElse(null);
    }


    @Transactional
    public void deleteById(Long id) {
        usersRepository.deleteById(id);
    }

    @Transactional
    public List<Users> gridSearch(String firstName,
                                  String lastName,
                                  String email,
                                  String username) {
        return usersRepository.findUsersByFirstnameOrLastnameOrEmailOrUsername(firstName, lastName, email, username);
    }

    @Transactional
    public byte[] getImage(String imageLink) {
        return UsersRepository.getImage(imageLink);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usersRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username#" + username + " not found!"));
    }

    @SneakyThrows
    @Transactional
    public void sendVerificationEmail(Users users, String siteURL) {
        String toAddress = users.getEmail();
        String fromAddress = "omidaliyousef@gmail.com";
        String senderName = "HomeService Company";
        String subject = "Please verify your registration";
        String content = "Dear [[name]],<br>"
                + "Please click the link below to verify your registration:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you,<br>"
                + "Home Service Company.";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("[[name]]", users.getFirstname() + " " + users.getLastname());
        String verifyURL = siteURL + "/users/verify?code=" + users.getVerificationCode();

        content = content.replace("[[URL]]", verifyURL);

        helper.setText(content, true);

        mailSender.send(message);

    }

    @Transactional
    public String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }

    @Transactional
    public boolean verify(String verificationCode) {
        Users user = usersRepository.findByVerificationCode(verificationCode);
        if (user == null || user.getVerified()) {
            return false;
        } else {
            user.setVerificationCode(null);
            user.setVerified(true);
            user.setUserStatus(UserStatus.AwaitingApproval);
            usersRepository.save(user);
            return true;
        }
    }
}

