package com.noaa.base.admin.controller;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JPanel;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.io.FileUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.BadSqlGrammarException;
//import org.springframework.security.crypto.codec.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.noaa.base.base.controller.BaseController;
import com.noaa.base.base.vo.BaseViewModel;
import com.noaa.base.common.service.CommonService;
import com.noaa.base.file.service.FileService;
import com.noaa.base.file.vo.AttachFileVO;
import com.noaa.base.file.vo.GsFileVO;

//import io.netty.handler.codec.base64.Base64Decoder;
//import sshm.com.service.CommonService;

@Controller("adminFileController")
public class AdminFileController extends BaseController {
	@Autowired
	private FileService fileService;
//	@Value("${file.temp.path}")
//	private String tempPath;
	@Value("${file.work.path}")
	private String workPath;
	
	@Value("#{resourceProp['file.temp.path']}")
	private String tempPath;
	
	@Autowired
	private CommonService comService;
	
	//work image
	private BufferedImage oriBuffer = null;
	private BufferedImage ediBuffer = null;
	private int imgWidth;
    private int imgHeight;
    private HashMap<String, Object> imgMap = new HashMap<String, Object>();
    private drawPanel dPanel;
    private long bytes;
    private long kilobyte;

	@RequestMapping(value = "/file/download/{fileSeq}", method = RequestMethod.GET)
	public void fileDownloadAction(@PathVariable int fileSeq,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AttachFileVO fileEntity = fileService.detail(fileSeq);
		File file = new File(fileEntity.getFile_path()+ fileEntity.getFile_new_name());
		if (file.isFile()) {
			String dispositionPrefix = "attachment; filename=";
			String filename = fileEntity.getFile_org_name();
			String browser = getBrowser(request);
			String encodedFilename = "";
			if (browser.equals("MSIE")) {
				encodedFilename = URLEncoder.encode(filename, "UTF-8")
						.replaceAll("\\+", "%20");
			} else if (browser.equals("Trident")) { // IE11 문자열 깨짐 방지
				encodedFilename = URLEncoder.encode(filename, "UTF-8")
						.replaceAll("\\+", "%20");
			} else if (browser.equals("Firefox")) {
				encodedFilename = "\""
						+ new String(filename.getBytes("UTF-8"), "8859_1")
						+ "\"";
				encodedFilename = URLDecoder.decode(encodedFilename);
			} else if (browser.equals("Opera")) {
				encodedFilename = "\""
						+ new String(filename.getBytes("UTF-8"), "8859_1")
						+ "\"";
			} else if (browser.equals("Chrome")) {
				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < filename.length(); i++) {
					char c = filename.charAt(i);
					if (c > '~') {
						sb.append(URLEncoder.encode("" + c, "UTF-8"));
					} else {
						sb.append(c);
					}
				}
				encodedFilename = sb.toString();
			} else if (browser.equals("Safari")) {
				encodedFilename = "\""
						+ new String(filename.getBytes("UTF-8"), "8859_1")
						+ "\"";
				encodedFilename = URLDecoder.decode(encodedFilename);
			} else {
				encodedFilename = "\""
						+ new String(filename.getBytes("UTF-8"), "8859_1")
						+ "\"";
			}

			response.setHeader("Content-Disposition", dispositionPrefix
					+ encodedFilename);
			if ("Opera".equals(browser)) {
				response.setContentType("application/octet-stream;charset=UTF-8");
			}

			OutputStream out = response.getOutputStream();
			FileInputStream fis = null;

			try {
				int temp;
				fis = new FileInputStream(file);
				while ((temp = fis.read()) != -1) {
					out.write(temp);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (fis != null) {
					try {
						fis.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}

	}
	
	@RequestMapping(value = "/main/file/download/{fileSeq}", method = RequestMethod.GET)
	public void sshmfileDownloadAction(@PathVariable int fileSeq,
	        HttpServletRequest request, HttpServletResponse response) throws Exception {

	    AttachFileVO fileEntity = fileService.sshmdetail(fileSeq);
	    File file = new File(fileEntity.getFile_url());

	        String dispositionPrefix = "attachment; filename=";
	        String filename = fileEntity.getFile_source();
	        String browser = getBrowser(request);
	        String encodedFilename = "";

	        // 파일 확장자 추출
	        String fileExtension = filename.substring(filename.lastIndexOf("."));

	        // 다운로드 받을 파일명 설정
	        String downloadFilename = filename.substring(0, filename.lastIndexOf(".")) + fileExtension;

	        if (browser.equals("MSIE")) {
	            encodedFilename = URLEncoder.encode(downloadFilename, "UTF-8").replaceAll("\\+", "%20");
	        } else if (browser.equals("Trident")) { // IE11 문자열 깨짐 방지
	            encodedFilename = URLEncoder.encode(downloadFilename, "UTF-8").replaceAll("\\+", "%20");
	        } else if (browser.equals("Firefox")) {
	            encodedFilename = "\"" + new String(downloadFilename.getBytes("UTF-8"), "8859_1") + "\"";
	            encodedFilename = URLDecoder.decode(encodedFilename);
	        } else if (browser.equals("Opera")) {
	            encodedFilename = "\"" + new String(downloadFilename.getBytes("UTF-8"), "8859_1") + "\"";
	        } else if (browser.equals("Chrome")) {
	            StringBuffer sb = new StringBuffer();
	            for (int i = 0; i < downloadFilename.length(); i++) {
	                char c = downloadFilename.charAt(i);
	                if (c > '~') {
	                    sb.append(URLEncoder.encode("" + c, "UTF-8"));
	                } else {
	                    sb.append(c);
	                }
	            }
	            encodedFilename = sb.toString();
	        } else if (browser.equals("Safari")) {
	            encodedFilename = "\"" + new String(downloadFilename.getBytes("UTF-8"), "8859_1") + "\"";
	            encodedFilename = URLDecoder.decode(encodedFilename);
	        } else {
	            encodedFilename = "\"" + new String(downloadFilename.getBytes("UTF-8"), "8859_1") + "\"";
	        }

	        response.setHeader("Content-Disposition", dispositionPrefix + encodedFilename);

	        if ("Opera".equals(browser)) {
	            response.setContentType("application/octet-stream;charset=UTF-8");
	        }

	        OutputStream out = response.getOutputStream();
	        FileInputStream fis = null;

	        try {
	            int temp;
	            fis = new FileInputStream(file);
	            while ((temp = fis.read()) != -1) {
	                out.write(temp);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            if (fis != null) {
	                try {
	                    fis.close();
	                } catch (Exception e) {
	                    e.printStackTrace();
	                }
	            }
	        }
	    
	}


	@RequestMapping(value = "/api/file/unzip/image/list", method = RequestMethod.POST)
	public @ResponseBody List<String> listUnzipFileAction(
			@RequestBody BaseViewModel vm, ModelMap model) {
		return fileService.listImageUnzipFiles(vm.getId());
	}

	@RequestMapping(value = "/file/drop/{fileSeq}", method = RequestMethod.POST)
	public void fileDropAction(@PathVariable int fileSeq,HttpServletRequest request, HttpServletResponse response) throws Exception {
		fileService.drop(fileSeq);
	}
	
	
	@RequestMapping(value = "/api/file/upload", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, String> createFile(BaseViewModel vm, ModelMap model) throws Exception {
		
		String rs = "";
		if(vm.getFileGroup()==null || vm.getFileGroup().equals("")){
			rs = fileService.create(vm.getUploadFiles());
		}else{
			rs = fileService.editFile(vm.getUploadFiles(),vm.getFileGroup());
		}
		
		List<String> paramFileGrp = new ArrayList<String>();
		paramFileGrp.add(rs);
		List<AttachFileVO> rsFile = fileService.list(paramFileGrp);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("file_grp_id", rs);
		map.put("file_grp_key", String.valueOf(rsFile.get(0).getFile_seq()));
		map.put("file_grp_key_max", String.valueOf(rsFile.get(rsFile.size()-1).getFile_seq()));
		return map;
	}

	@RequestMapping(value = "/file/download/group/{fileGrp}", method = RequestMethod.POST)
	public void fileGrpDownloadAction(@PathVariable String fileGrp, 
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<String> paramFileGrp = new ArrayList<String>();
		paramFileGrp.add(fileGrp);
		List<AttachFileVO> files = fileService.list(paramFileGrp);
		if (files.size() == 1) {
			AttachFileVO fileEntity = files.get(0);
			File file = new File(fileEntity.getFile_path()+ fileEntity.getFile_new_name());
			if (file.isFile()) {
				String dispositionPrefix = "attachment; filename=";
				String filename = fileEntity.getFile_org_name();
				String browser = getBrowser(request);
				String encodedFilename = "";
				if (browser.equals("MSIE")) {encodedFilename = URLEncoder.encode(filename, "UTF-8").replaceAll("\\+", "%20");
				} else if (browser.equals("Trident")) { // IE11 문자열 깨짐 방지
					encodedFilename = URLEncoder.encode(filename, "UTF-8").replaceAll("\\+", "%20");
				} else if (browser.equals("Firefox")) {
					encodedFilename = "\""+ new String(filename.getBytes("UTF-8"), "8859_1")+ "\"";
					encodedFilename = URLDecoder.decode(encodedFilename);
				} else if (browser.equals("Opera")) {
					encodedFilename = "\""+ new String(filename.getBytes("UTF-8"), "8859_1")+ "\"";
				} else if (browser.equals("Chrome")) {
					StringBuffer sb = new StringBuffer();
					for (int i = 0; i < filename.length(); i++) {
						char c = filename.charAt(i);
						if (c > '~') {
							sb.append(URLEncoder.encode("" + c, "UTF-8"));
						} else {
							sb.append(c);
						}
					}
					encodedFilename = sb.toString();
				} else if (browser.equals("Safari")) {encodedFilename = "\""+ new String(filename.getBytes("UTF-8"), "8859_1")+ "\"";
					encodedFilename = URLDecoder.decode(encodedFilename);
				} else {
					encodedFilename = "\""+ new String(filename.getBytes("UTF-8"), "8859_1")
							+ "\"";
				}

				response.setHeader("Content-Disposition", dispositionPrefix
						+ encodedFilename);
				if ("Opera".equals(browser)) {
					response.setContentType("application/octet-stream;charset=UTF-8");
				}

				OutputStream out = response.getOutputStream();
				FileInputStream fis = null;

				try {
					int temp;
					fis = new FileInputStream(file);
					while ((temp = fis.read()) != -1) {
						out.write(temp);
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (fis != null) {
						try {
							fis.close();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		} else if (files.size() > 1) {
			
			response.setHeader("Content-Disposition", "attachment;filename=download.zip;"); 
			OutputStream out = response.getOutputStream(); 
			FileInputStream fis = null;
			try { 
				int temp; fis = new FileInputStream(fileService.createZip(fileGrp)); 
			while ((temp= fis.read()) != -1) { 
				out.write(temp); 
				} 
			} catch (Exception e) {
				e.printStackTrace(); 
				} 
			finally { 
				if (fis != null) { 
				try {
					fis.close(); 
				} catch (Exception e){ 
				  e.printStackTrace(); 
				} 
			} 
		}
		}

		
	}

	public String getBrowser(HttpServletRequest request) {
		String header = request.getHeader("User-Agent");
		if (header.indexOf("MSIE") > -1) {
			return "MSIE";
		} else if (header.indexOf("Trident") > -1) { // IE11 문자열 깨짐 방지
			return "Trident";
		} else if (header.indexOf("Chrome") > -1) {
			return "Chrome";
		} else if (header.indexOf("Opera") > -1) {
			return "Opera";
		} else if (header.indexOf("Safari") > -1) {
			return "Safari";
		}
		return "Firefox";
	}
	
	@RequestMapping(value="/filegroup.do",method=RequestMethod.POST)
	public @ResponseBody String fileGroupId(){
		return UUID.randomUUID().toString();
	}

	/*
	 * @RequestMapping(value = "/main/file/upload.do", method = RequestMethod.POST)
	 * public @ResponseBody HashMap<String, Object> sshmCreateFile(BaseViewModel vm,
	 * ModelMap model) throws Exception { List<AttachFileDTO> rs;
	 * if(vm.getKey()!=null) { rs = fileService.sshmcreate(vm.getUploadFiles(),
	 * vm.getFileTitle(), vm.getKey(), vm.getFileGroup()); } else { rs =
	 * fileService.sshmcreate(vm.getUploadFiles(), vm.getFileGroup()); }
	 * 
	 * HashMap<String, Object> map = new HashMap<String, Object>();
	 * map.put("filelist", rs); return map; }
	 */
	
	//GS 네오텍 파일업로드
	/*
	 * 이 로직을 사용하고 계신다면 이수훈 사원을 찾아주세요. 
	 */
	@RequestMapping(value = "/main/file/upload.do", method = RequestMethod.POST)
	public @ResponseBody HashMap<String, Object> gsCreateFile(BaseViewModel vm, ModelMap model) {
		List<GsFileVO> rs;
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			if(vm.getKey()!=null) {
				rs = fileService.gscreate(vm.getUploadFiles(), vm.getFileTitle(), vm.getKey(), vm.getFileGroup(), vm.getMapperId(), vm.getUser_id(),vm.getMode(),vm.getSn() );
			} else {
				rs = fileService.gscreate(vm.getUploadFiles(), vm.getFileGroup(),vm.getMapperId(), vm.getUser_id(),vm.getMode(),vm.getSn(),vm.getBplc_sn());
			}
			rs.get(0).setSe("success");
			map.put("filelist", rs);
			map.put("success", rs.get(0).getSe());
		}catch (NullPointerException n) {
			logger.error(n.getMessage());
//			fileService.gsCreateRollback();
		}catch (BadSqlGrammarException sql) {
			logger.error(sql.getSql());
		}
		
		return map;
	}
	//img merge
	@RequestMapping(value = "/imgEdit", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, Object> imgEdit(@RequestBody BaseViewModel vm, ModelMap model) throws Exception {
		//폴더 생성
		List<AttachFileVO> rs;
		
		String originType;
		BufferedImage originImg = null;
		BufferedImage editImg = null;
		String tempOrginName = null;
		String tempEditName = null;
		String nullStr = null;
		String[] originSt = null;
		SimpleDateFormat dayForm = new SimpleDateFormat("yyyyMMddHHmmss");
		Date today = new Date();
		String writeDay = dayForm.format(today);
		int originWidth = 0;
		int originHeight = 0; 
		
		String orgFileUpdtYn = "N";	//기존파일수정여부
		String editFileUpdtYn = "N";	//캔버스파일수정여부
		String fileContent = "";
		String fileGrp = "";
		
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> data = null;
		
		//편집 여부체크
		editFileUpdtYn = vm.getEditableClickYn(); 
		if(vm.getMode().equals("update")) {
			param.put("uid", vm.getUid());
			List<Map<String,Object>> list = null;
			//System.err.println(vm.getGbn());
			
			if(vm.getGbn().equals("opin")) {
				list = comService.List("opin.opinFileList", (HashMap<String, Object>) param);
			} else {
				list = comService.List("coach.coachFileList", (HashMap<String, Object>) param);
			}
			if(list.size() > 0) {
				data = list.get(0);
			}
		}
		
		
		if(vm.getOriginImg() == nullStr || vm.getOriginImg().equals("")) {
			orgFileUpdtYn = "N";
			String filePath = data.get("filepath").toString() + data.get("filename").toString(); //DB에서 조회한 파일경로
			originType = data.get("ext").toString();
			filePath = filePath.replaceAll("/", Matcher.quoteReplacement(File.separator));
			File file = new File(filePath);
		    //로컬 파일을 사용하는 경우 
		    oriBuffer = ImageIO.read(file);	       
		    
		}else {
			orgFileUpdtYn = "Y";
			originSt = vm.getOriginImg().split(",");
			fileContent = originSt[1];
			switch(originSt[0]) {
				case "data:image/jpeg;base64":
					originType = "jpeg";
		            break;
		        case "data:image/png;base64":
		        	originType = "png";
		            break;
		        default:
		        	originType = "jpg";
		            break;
			}
			//Base64 읽어서 BufferedImage 에 base64 넣기
			byte[] orginBtyeArr = DatatypeConverter.parseBase64Binary(fileContent);
			ByteArrayInputStream oriBis =  new ByteArrayInputStream(orginBtyeArr);
			oriBuffer = ImageIO.read(oriBis);
			
		    fileGrp = UUID.randomUUID().toString();
		    
		    tempOrginName = "work_"+ writeDay +"_origin_" + fileGrp + "." + originType;
		    File originalFile = new File(workPath + File.separator + tempOrginName);
			ImageIO.write(oriBuffer, originType, originalFile);
			
			bytes = originalFile.length();
			kilobyte = bytes/1024;
			oriBis.close();
		}
		
		//수정을 했거나 신규등록일 경우
//		if(editFileUpdtYn.equals("Y") || !vm.getMode().equals("update")) {
		String[] editSt = vm.getEditImg().split(",");
		
		byte[] eidtBtyeArr = DatatypeConverter.parseBase64Binary(editSt[1]);
		ByteArrayInputStream ediBis =  new ByteArrayInputStream(eidtBtyeArr);
		ediBuffer = ImageIO.read(ediBis);
		
		fileGrp = UUID.randomUUID().toString();
	    
		tempEditName = "work_"+ writeDay +"_edit_" + fileGrp + "." + originType;
	    File editFile = new File(workPath + File.separator + tempEditName);
		ImageIO.write(ediBuffer, "png", editFile);				
		
		imgWidth = vm.getCanvasWidth();
		imgHeight = vm.getCanvasHeight();
		originWidth = oriBuffer.getWidth();
		originHeight = oriBuffer.getHeight();
		
		
		ediBis.close();

		
		//img 합치기
		if(!vm.getMode().equals("update") || !(orgFileUpdtYn == "N" && editFileUpdtYn == "N")) {
			dPanel = new drawPanel();
        	imgMap = dPanel.paint(null, writeDay);
		}
		
		imgMap.put("width", imgWidth);
		imgMap.put("height", imgHeight);
		imgMap.put("overlay_width", imgWidth);
		imgMap.put("overlay_height", imgHeight);
		imgMap.put("orginImgName", tempOrginName);
		imgMap.put("editImgName", tempEditName);
		imgMap.put("bytes", bytes);
		imgMap.put("kilobyte", kilobyte);
		imgMap.put("originType", originType);
		imgMap.put("originWidth", originWidth);
		imgMap.put("originHeight", originHeight);
		imgMap.put("workPath", workPath + '/');
		
		
		System.err.println("imgMap> " + imgMap);
		return imgMap;
	}
	
	public class drawPanel extends JPanel {
		public HashMap<String, Object> paint (Graphics g, String writeDay) {
			//paint()는 그래픽 출력 이벤트
			super.paint(g);
			String tempOverName = null;
			long overlay_bytes = 0;
			long overlay_kilobyte = 0;
			
			BufferedImage overBuffer = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_RGB);
			overBuffer.getScaledInstance(imgWidth, imgHeight, BufferedImage.SCALE_SMOOTH);
			g = overBuffer.createGraphics();
			
			//이미지 합치기 (배경 >> 그림 순서)
			g.drawImage(oriBuffer, 0, 0, imgWidth, imgHeight, null);
			g.drawImage(ediBuffer, 0, 0, imgWidth, imgHeight, null);
			
			g.dispose();
			try {
				String fileGrp = UUID.randomUUID().toString();
				tempOverName = "work_"+ writeDay +"_overlay_" + fileGrp + ".png";
				File tempOverImg = new File(workPath + File.separator + tempOverName);
				ImageIO.write(overBuffer, "png", tempOverImg);
				
				overlay_bytes = tempOverImg.length();
				overlay_kilobyte = overlay_bytes/1024;
			}catch(Exception e) {
				logger.debug(e.getMessage());
			}
			
			System.err.println(">>>overlay_bytes>>>>>" + overlay_bytes);
			System.err.println(">>>overlay_kilobyte>>>>>" + overlay_kilobyte);
			
			imgMap.put("overlayImgName", tempOverName);
			imgMap.put("overlay_bytes", overlay_bytes);
			imgMap.put("overlay_kilobyte", overlay_kilobyte);
			
			return imgMap;
		}
	}
	
	public static byte[] convertFileContentToBlob (String filePath) {

        byte[] result = null;
        try {
            result = FileUtils.readFileToByteArray( new File(filePath));
        } catch (IOException ie) {
        	logger.debug(ie.getMessage());
        }

        return result;
    }

    public static String convertBlobToBase64 (byte[] blob) {
        return new String(Base64.getEncoder().encode(blob));
    }

    public String getFileContent (String filePath) {
        byte[] filebyte = convertFileContentToBlob(filePath);
        return convertBlobToBase64(filebyte);
    }
    
    
    @RequestMapping(value = "/download.do", method = RequestMethod.POST)
	public void download(@RequestBody HashMap<String, Object> param, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	
		GsFileVO fileEntity = fileService.fileInfo(param);
		
		//gs d드라이브 구축으로 인해 소스 추가
		fileEntity.setStrg_path(fileEntity.getStrg_path().replace("C:", "D:")); 
		File file = new File(fileEntity.getStrg_path()+ fileEntity.getStrg_file_nm());
		if (file.isFile()) {
			String dispositionPrefix = "attachment; filename=\"";
			String filename = fileEntity.getOrgnl_file_nm();
			String browser = getBrowser(request);
			String encodedFilename = "";
			if (browser.equals("MSIE")) {
				encodedFilename = URLEncoder.encode(filename, "UTF-8")
						.replaceAll("\\+", "%20");
			} else if (browser.equals("Trident")) { // IE11 문자열 깨짐 방지
				encodedFilename = URLEncoder.encode(filename, "UTF-8")
						.replaceAll("\\+", "%20");
			} else if (browser.equals("Firefox")) {
				encodedFilename = "\""
						+ new String(filename.getBytes("UTF-8"), "8859_1")
						+ "\"";
				encodedFilename = URLDecoder.decode(encodedFilename);
			} else if (browser.equals("Opera")) {
				encodedFilename = "\""
						+ new String(filename.getBytes("UTF-8"), "8859_1")
						+ "\"";
			} else if (browser.equals("Chrome")) {
				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < filename.length(); i++) {
					char c = filename.charAt(i);
					if (c > '~') {
						sb.append(URLEncoder.encode("" + c, "UTF-8"));
					} else {
						sb.append(c);
					}
				}
				encodedFilename = sb.toString();
			} else if (browser.equals("Safari")) {
				encodedFilename = "\""
						+ new String(filename.getBytes("UTF-8"), "8859_1")
						+ "\"";
				encodedFilename = URLDecoder.decode(encodedFilename);
			} else {
				encodedFilename = "\""
						+ new String(filename.getBytes("UTF-8"), "8859_1")
						+ "\"";
			}

			response.setHeader("Content-Disposition", dispositionPrefix
					+ encodedFilename+"\"");
			if ("Opera".equals(browser)) {
				response.setContentType("application/octet-stream;charset=UTF-8");
			}
			
			response.setCharacterEncoding("UTF-8");
			
			
			OutputStream out = response.getOutputStream();
			FileInputStream fis = null;

			try {
				int temp;
				fis = new FileInputStream(file);
				while ((temp = fis.read()) != -1) {
					out.write(temp);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (fis != null) {
					try {
						fis.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}

	}

    @RequestMapping(value = "/downloadForm.do", method = RequestMethod.POST)
	public void downloadForm(@RequestBody HashMap<String, Object> param, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	
		File file = new File(tempPath + "/form/" + param.get("gbn")+ ".xlsx");
	
		if (file.isFile()) {
			String dispositionPrefix = "attachment; filename=\"";
//			String filename = "";
//			
//			if(param.get("gbn").equals("fstRskEvltRgstrForm")) {
//				filename = "최초위험성평가양식.xlsx";
//			}
//			String browser = getBrowser(request);
			String encodedFilename = "";
//			if (browser.equals("MSIE")) {
//				encodedFilename = URLEncoder.encode(filename, "UTF-8")
//						.replaceAll("\\+", "%20");
//			} else if (browser.equals("Trident")) { // IE11 문자열 깨짐 방지
//				encodedFilename = URLEncoder.encode(filename, "UTF-8")
//						.replaceAll("\\+", "%20");
//			} else if (browser.equals("Firefox")) {
//				encodedFilename = "\""
//						+ new String(filename.getBytes("UTF-8"), "8859_1")
//						+ "\"";
//				encodedFilename = URLDecoder.decode(encodedFilename);
//			} else if (browser.equals("Opera")) {
//				encodedFilename = "\""
//						+ new String(filename.getBytes("UTF-8"), "8859_1")
//						+ "\"";
//			} else if (browser.equals("Chrome")) {
//				StringBuffer sb = new StringBuffer();
//				for (int i = 0; i < filename.length(); i++) {
//					char c = filename.charAt(i);
//					if (c > '~') {
//						sb.append(URLEncoder.encode("" + c, "UTF-8"));
//					} else {
//						sb.append(c);
//					}
//				}
//				encodedFilename = sb.toString();
//			} else if (browser.equals("Safari")) {
//				encodedFilename = "\""
//						+ new String(filename.getBytes("UTF-8"), "8859_1")
//						+ "\"";
//				encodedFilename = URLDecoder.decode(encodedFilename);
//			} else {
//				encodedFilename = "\""
//						+ new String(filename.getBytes("UTF-8"), "8859_1")
//						+ "\"";
//			}
//
//			response.setHeader("Content-Disposition", dispositionPrefix
//					+ encodedFilename+"\"");
//			if ("Opera".equals(browser)) {
//				response.setContentType("application/octet-stream;charset=UTF-8");
//			}
			
			response.setCharacterEncoding("UTF-8");
			
			
			OutputStream out = response.getOutputStream();
			FileInputStream fis = null;

			try {
				int temp;
				fis = new FileInputStream(file);
				while ((temp = fis.read()) != -1) {
					out.write(temp);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (fis != null) {
					try {
						fis.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}

	}
    
    @RequestMapping(value="/downloadPdf.do",method=RequestMethod.POST)
	public @ResponseBody ResponseEntity<byte[]> downloadPdf(@RequestBody Map<String, Object> paramMap ) throws IOException{
		
		List<HashMap<String, Object>> imgData = (List<HashMap<String, Object>>) paramMap.get("imgData");

        PDDocument document = new PDDocument();
        for(HashMap<String, Object> img : imgData) {
        	String base64Image = (String) img.get("img");
        	base64Image = base64Image.split(",")[1];
        	
	        byte[] imageBytes = Base64.getDecoder().decode(base64Image);
	        ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
	        BufferedImage bufferedImage = ImageIO.read(bis);
	
	        PDRectangle a4 = PDRectangle.A4;
	        float pageWidth = a4.getWidth();
	        float pageHeight = a4.getHeight();
	        float imgWidthRatio = 1;
	
	        float imageWidth = bufferedImage.getWidth();
	        float imageHeight = bufferedImage.getHeight();
	
	        if (pageWidth < imageWidth) {
	        	imgWidthRatio = (float) imageWidth / (float) pageWidth;
	        }
	        
	        int yPosition = 0;	//축소한 이미지
	        int orgImg_y = 0;	//org이미지
	        
	        
	        while (yPosition < imageHeight) {
	        	
	        	if(paramMap.get("type").equals("p")) {
		            PDPage page = new PDPage(new PDRectangle(pageWidth, pageHeight));
		            document.addPage(page);
		            PDPageContentStream contentStream = new PDPageContentStream(document, page);
		            
		            if(yPosition != 0){
		            	//
		            	yPosition = (int)((yPosition / imgWidthRatio) - 20);
		            	//이미지 y 값 계산하기.
		            	orgImg_y = orgImg_y - 10;
		            }
		            
		            float drawHeight = 0; // pdf에 그릴 이미지 height
		            BufferedImage subImage = null;
		            
		            if(pageHeight > ((imageHeight / imgWidthRatio) - yPosition)) {
		            	drawHeight = (int)((imageHeight / imgWidthRatio) - (yPosition));
		            	subImage = bufferedImage.getSubimage(0, orgImg_y, (int)imageWidth, (int)(imageHeight - orgImg_y)); //원본이미지 크기
		            	
		            	yPosition += imageHeight;
		            }else {
		            	drawHeight = (int)pageHeight - 20;	
		            	subImage = bufferedImage.getSubimage(0, orgImg_y, (int)imageWidth, (int)(pageHeight * imgWidthRatio)); //원본이미지 크기 
		            	
		            	yPosition = orgImg_y +(int)(pageHeight * imgWidthRatio);
		            	orgImg_y += (int)(pageHeight * imgWidthRatio);
		            }
		            
		            PDImageXObject pdImage = PDImageXObject.createFromByteArray(document, getByteArrayFromImage(subImage), "image");
		
		            contentStream.drawImage(pdImage, 10, pageHeight - drawHeight - 10 , (pageWidth) - 20, drawHeight); //이미지, x,y, width, height
		            contentStream.close();
	        	}else {
	        		PDPage page = new PDPage(new PDRectangle(pageHeight, pageWidth));
	                document.addPage(page);
	                PDPageContentStream contentStream = new PDPageContentStream(document, page);
	                
	                if(yPosition != 0){
	                	//
	                	yPosition = (int)((yPosition / imgWidthRatio) - 20);
	                	//이미지 y 값 계산하기.
	                	orgImg_y = orgImg_y - 10;
	                }
	                
	                float drawHeight = 0; // pdf에 그릴 이미지 height
	                BufferedImage subImage = null;
	                
	                if(pageWidth > ((imageHeight / imgWidthRatio) - yPosition)) {
	                	drawHeight = (int)((imageHeight / imgWidthRatio) - (yPosition));
	                	subImage = bufferedImage.getSubimage(0, orgImg_y, (int)imageWidth, (int)(imageHeight - orgImg_y)); //원본이미지 크기
	                	
	                	yPosition += imageHeight;
	                }else {
	                	drawHeight = (int)pageWidth - 20;	
	                	subImage = bufferedImage.getSubimage(0, orgImg_y, (int)imageWidth, (int)(pageWidth * imgWidthRatio)); //원본이미지 크기 
	                	
	                	yPosition = orgImg_y +(int)(pageWidth * imgWidthRatio);
	                	orgImg_y += (int)(pageWidth * imgWidthRatio);
	                }
	                
	                PDImageXObject pdImage = PDImageXObject.createFromByteArray(document, getByteArrayFromImage(subImage), "image");

	                contentStream.drawImage(pdImage, 10, pageWidth - drawHeight - 10 , (pageHeight) - 20, drawHeight); //이미지, x,y, width, height
	                contentStream.close();
	        	}
	        }
        }
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        document.save(baos);
        document.close();
        
        HttpHeaders headers = new HttpHeaders();
        MediaType mediaType = new MediaType("application", "pdf"); // PDF Media Type 정의
        headers.setContentType(mediaType);
        headers.setContentDispositionFormData("attachment", "output.pdf");
        headers.setContentLength(baos.size());

        return new ResponseEntity<>(baos.toByteArray(), headers, HttpStatus.OK);
    }

    private byte[] getByteArrayFromImage(BufferedImage image) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
        return baos.toByteArray();
    }
}
