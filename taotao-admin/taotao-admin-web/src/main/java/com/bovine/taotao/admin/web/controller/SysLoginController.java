package com.bovine.taotao.admin.web.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import com.alibaba.fastjson2.JSON;
import com.bovine.taotao.common.core.Constant;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bovine.taotao.admin.web.event.SysLoginEvent;
import com.bovine.taotao.admin.web.model.LoginModel;
import com.bovine.taotao.common.core.R;
import com.bovine.taotao.common.core.S;
import com.google.code.kaptcha.Producer;

/**
 * 管理系统登陆验证码控制器
 * @author eden
 * @time 2022年7月22日 上午11:36:39
 */
@RestController
@RequestMapping("/sys")
public class SysLoginController extends BaseController implements ApplicationEventPublisherAware {

	@Autowired
	private Producer producer;

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	@Autowired
	private AuthenticationManager authenticationManager;

	private ApplicationEventPublisher applicationEventPublisher;

	/**
	 * 获取图片码
	 * @param response
	 * @param uuid
	 * @throws Exception
	 */
	@GetMapping("/captcha.jpg")
	public void captcha(HttpServletResponse response, @Validated @NotBlank(message = "uuid不能为空!") @RequestParam String uuid) throws Exception {
		String text = this.producer.createText();
		String cap = text.substring(0, text.lastIndexOf("@"));
		String code = text.substring(text.lastIndexOf("@") + 1);
		BufferedImage image = this.producer.createImage(cap);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ImageIO.write(image, "jpg", bos);
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/gif");
		ServletOutputStream sos = response.getOutputStream();
		sos.write(bos.toByteArray());
		sos.flush();
		sos.close();
		redisTemplate.opsForValue().set(uuid, code, 5, TimeUnit.MINUTES);
	}

	/**
	 * 登陆
	 * @param lm
	 * @return
	 */
	/*@PostMapping("/login")
	public R login(@RequestBody @Validated LoginModel lm) {
		this.applicationEventPublisher.publishEvent(new SysLoginEvent(lm));
		String captcha = redisTemplate.opsForValue().get(lm.getUuid());
		if(captcha == null || captcha.isBlank()) {
			return R.error(S.CODE_EXPIRE);
		}
		if(!captcha.equalsIgnoreCase(lm.getCaptcha())) {
			return R.error(S.CODE_ERROR);
		}
		SysUser user = sysUserService.getByUserName(lm.getUsername());
		if(user == null || !user.getPassword().equals(new Sha256Hash(lm.getPassword(), user.getSalt()).toHex())) {
			return R.error(S.USER_PWD_ERROR);
		}

		if(user.getStatus() == 1) {
			return R.error(S.USER_INACTIVE_ERROR);
		}
		String token = shiroService.createToken(user);
		return R.ok(token);
	}*/

	@PostMapping("/login")
	public R login(@RequestBody @Validated LoginModel lm) {
		this.applicationEventPublisher.publishEvent(new SysLoginEvent(lm));
		String captcha = redisTemplate.opsForValue().get(lm.getUuid());
		if(captcha == null || captcha.isBlank()) {
			return R.error(S.CODE_EXPIRE);
		}
		redisTemplate.delete(lm.getUuid()); //删除图片
		if(!captcha.equalsIgnoreCase(lm.getCaptcha())) {
			return R.error(S.CODE_ERROR);
		}
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(lm.getUsername(), lm.getPassword());
		Authentication authenticate = authenticationManager.authenticate(authenticationToken);
		if(authenticate == null) {
			return R.error(S.USER_PWD_ERROR);
		}
		Object principal = authenticate.getPrincipal();
		String token = UUID.randomUUID().toString().replace("-", "");
		redisTemplate.opsForValue().set(Constant.RedisKey.SYS_SESSION_ID_STR_KEY + token, JSON.toJSONString(principal), 12, TimeUnit.HOURS);
		return R.ok(token);
	}

	/**
	 * 退出登陆
	 * @param request
	 * @return
	 */
	/*@PostMapping("/logout")
	public R logout(HttpServletRequest request) {
		this.applicationEventPublisher.publishEvent(new SysLoginEvent(getUser().getUsername()));
		String token = request.getHeader("token");
		shiroService.remove(token);
		SecurityUtils.getSubject().logout();
		return R.ok();
	}*/

	@PostMapping("/logout")
	public R logout(HttpServletRequest request) {
		this.applicationEventPublisher.publishEvent(new SysLoginEvent(getUser().getUsername()));
		String token = request.getHeader("token");
		redisTemplate.delete(Constant.RedisKey.SYS_SESSION_ID_STR_KEY + token);
		return R.ok();
	}

	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.applicationEventPublisher = applicationEventPublisher;
	}
}
