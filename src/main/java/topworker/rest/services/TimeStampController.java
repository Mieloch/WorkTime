package topworker.rest.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import topWorker.restClient.model.ClientDetails;
import topworker.model.entity.TimeStamp;
import topworker.model.entity.User;
import topworker.persistance.TimeStampDao;
import topworker.persistance.UserDao;

@RestController
@RequestMapping("/time/stamp/")
public class TimeStampController {

	@Autowired
	private TimeStampDao timeStampDao;

	@Autowired
	private UserDao userDao;

	@RequestMapping(value = "/post")
	public ResponseEntity<TimeStamp> get() {

		TimeStamp car = new TimeStamp();

		return new ResponseEntity<TimeStamp>(car, HttpStatus.OK);
	}

	@RequestMapping(value = "/post", method = RequestMethod.POST)
	public ResponseEntity<TimeStamp> postTest(@RequestBody ClientDetails clientDetails) {
		System.out.println("post");
		TimeStamp timeStamp = new TimeStamp(clientDetails);
		User user = userDao.getUserById(clientDetails.getUserId());
		timeStamp.setUser(user);
		timeStampDao.postTime(timeStamp);
		return new ResponseEntity<TimeStamp>(timeStamp, HttpStatus.OK);
	}

}
