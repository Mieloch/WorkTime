package topworker.controller;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * Created by echomil on 21.03.16.
 */
@RestController
@RequestMapping("/api/")
public class FIleController {

    @RequestMapping(value = "/file/{file_name}", method = RequestMethod.GET)
    public void getFile(@PathVariable("file_name") String fileName, HttpServletResponse response) {
        try {

            InputStream is = new FileInputStream("/home/echomil/repo/java/TopWorkerRestClient/out/artifacts/TopWorkerRestClient.zip");
            IOUtils.copy(is, response.getOutputStream());
            response.flushBuffer();
        } catch (Exception e) {

        }
    }
}
