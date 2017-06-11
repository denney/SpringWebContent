package hello;

import domain.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class FileController {
    private static final Logger logger = LoggerFactory.getLogger(FileController.class);
    @Value("${imgPath.Prefix}")
    private String filepath1;

    public static Map<String, Object> resultJson(int resultCode, String messageValue, Object objectValue) {
        Map map = new HashMap();
        map.put("resultCode", resultCode);
        map.put("message", messageValue);
        map.put("data", objectValue);
        return map;
    }

    @RequestMapping("/greeting")
    public String greeting(@RequestParam(value = "name", required = false, defaultValue = "World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }

    //文件上传相关代码
    @RequestMapping(value = "upload")
    @ResponseBody
    public Map upload(@RequestParam("test") MultipartFile file, User user) {
        System.out.println("类======" + user);

            user.setImgPath(filepath1);


        if (file.isEmpty()) {
            return resultJson(102, "文件为空", user);
        }
        // 获取文件名
        String fileName = file.getOriginalFilename();
        logger.info("上传的文件名为：" + fileName);
        // 获取文件的后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        logger.info("上传的后缀名为：" + suffixName);
        // 文件上传后的路径
        String filePath = "/Users/admin/Desktop/a/";
        // 解决中文问题，liunx下中文路径，图片显示问题
        // fileName = UUID.randomUUID() + suffixName;
        File dest = new File(filePath + fileName);
        // 检测是否存在目录
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);

        } catch (Exception e) {
            return resultJson(101, "上传图片失败", user);
        }


        try {
//          User user= userService.save(user);
            user.setId(1L);
        } catch (Exception e) {
            return resultJson(103, "图片上传成功，数据库连接异常", user);
        }


        return resultJson(100, "图片上传成功，数据保存成功", user);
    }

    //文件下载相关代码
    @RequestMapping("/download")
    public String downloadFile(org.apache.catalina.servlet4preview.http.HttpServletRequest request, HttpServletResponse response) {
        String fileName = "FileUploadTests.java";
        if (fileName != null) {
            //当前是从该工程的WEB-INF//File//下获取文件(该目录可以在下面一行代码配置)然后下载到C:\\users\\downloads即本机的默认下载的目录
            String realPath = request.getServletContext().getRealPath(
                    "//WEB-INF//");
            File file = new File(realPath, fileName);
            if (file.exists()) {
                response.setContentType("application/force-download");// 设置强制下载不打开
                response.addHeader("Content-Disposition",
                        "attachment;fileName=" + fileName);// 设置文件名
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                    System.out.println("success");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return null;
    }

    //多文件上传方法一  下载到该工程的所在目录下
    @RequestMapping(value = "/batch/upload", method = RequestMethod.POST)
    @ResponseBody
    public String handleFileUpload(HttpServletRequest request) {
        List<MultipartFile> files = ((MultipartHttpServletRequest) request)
                .getFiles("file");
        MultipartFile file = null;
        BufferedOutputStream stream = null;
        for (int i = 0; i < files.size(); ++i) {
            file = files.get(i);
            if (!file.isEmpty()) {
                try {
                    byte[] bytes = file.getBytes();
                    stream = new BufferedOutputStream(new FileOutputStream(
                            new File(file.getOriginalFilename())));
                    stream.write(bytes);
                    stream.close();

                } catch (Exception e) {
                    stream = null;
                    return "You failed to upload " + i + " => "
                            + e.getMessage();
                }
            } else {
                return "You failed to upload " + i
                        + " because the file was empty.";
            }
        }
        return "upload successful";
    }

    //多文件上传方法二
    @RequestMapping(value = "/batchupload", method = RequestMethod.POST)
    @ResponseBody
    public String batchFileUp(HttpServletRequest request) {
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        //String contextPath = request.getSession().getServletContext().getRealPath("/")+ "\\uploadsTest";//可以将文件存放在这个目录下  是当前工程下的uploadtest目录下
//        System.out.println(contextPath);
//        File tempFile = new File(contextPath);
//        if(!tempFile.exists())
//        {
//            tempFile.mkdir();
//        }
        for (int i = 0; i < files.size(); i++) {
            MultipartFile file = files.get(i);
            System.out.println(file.getContentType());
            String fileName = file.getOriginalFilename();
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            String fullfilePath = "E://test//" + fileName;//自己设定的文件目录
            logger.info("fullfilePath:", fullfilePath);
            if (!file.isEmpty()) {
                try {
                    BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(fullfilePath)));
                    stream.write(file.getBytes());
                    stream.close();
                } catch (Exception e) {
                    return "Failed to Upload" + fileName + "=>" + e.getMessage();
                }
            } else {
                return "Failed to Upload " + fileName + "because the file was empty";
            }

        }
        return "Upload Success!";
    }
}