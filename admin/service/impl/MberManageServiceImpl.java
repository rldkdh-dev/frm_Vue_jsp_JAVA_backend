package com.noaa.base.admin.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.noaa.base.account.dao.UserDao;
import com.noaa.base.account.service.AccountService;
import com.noaa.base.account.vo.UserVO;
import com.noaa.base.admin.dao.MberManageDAO;
import com.noaa.base.admin.service.EgovMberManageService;
import com.noaa.base.admin.vo.MberManageVO;
import com.noaa.base.admin.vo.UserDefaultVO;
import com.noaa.base.utils.FileScrty;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;

/**
 * 일반회원관리에 관한비지니스클래스를 정의한다.
 * @author 공통서비스 개발팀 조재영
 * @since 2009.04.10
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2009.04.10  JJY            최초 생성
 *   2011.08.31  JJY            경량환경 템플릿 커스터마이징버전 생성
 *
 * </pre>
 */
@Service("mberManageService")
public class MberManageServiceImpl extends EgovAbstractServiceImpl implements EgovMberManageService {

	@Autowired
	private AccountService userService;
	
	/** mberManageDAO */
	@Resource(name="mberManageDAO")
	private MberManageDAO mberManageDAO;

	@Resource(name="userDao")
	private UserDao userDao;

	/** egovUsrCnfrmIdGnrService */
	@Resource(name="egovUsrCnfrmIdGnrService")
	private EgovIdGnrService idgenService;

	/**
	 * 사용자의 기본정보를 화면에서 입력하여 항목의 정합성을 체크하고 데이터베이스에 저장
	 * @param mberManageVO 일반회원 등록정보
	 * @return result 등록결과
	 * @throws Exception
	 */
	
	@Override
	public String insertMber(MberManageVO mberManageVO) throws Exception  {
		//고유아이디 셋팅
		String uniqId = idgenService.getNextStringId();
		mberManageVO.setUniqId(uniqId);
		//패스워드 암호화
		String pass = FileScrty.encryptPassword(mberManageVO.getPassword(), mberManageVO.getMberId());
		mberManageVO.setPassword(pass);

		String result = mberManageDAO.insertMber(mberManageVO);
		return result;
	}
	
	@Override
	public int insertMber(UserVO paramDTO) throws Exception  {
		String password=FileScrty.encryptPassword(paramDTO.getUser_pwd(),paramDTO.getUser_id());
		paramDTO.setUser_pwd(password);
		MberManageVO mberManageVO =new MberManageVO();
		mberManageVO.setMberId(paramDTO.getUser_id());
		mberManageVO.setMberNm(paramDTO.getUser_name());
		mberManageVO.setMberEmailAdres(paramDTO.getUser_email());
		mberManageVO.setMoblphonNo(paramDTO.getUser_phone());
		mberManageVO.setPassword(password);
		mberManageVO.setPasswordHint("P00");
		mberManageVO.setPasswordCnsr("none");
		mberManageVO.setMberSttus("P");
		
		//고유아이디 셋팅
		String uniqId = idgenService.getNextStringId();
		mberManageVO.setUniqId(uniqId);
		
		HashMap<String,Object> param = null;
		
		int rs=userService.create(paramDTO);
		String result = "";
		if(rs == 1) result = mberManageDAO.insertMber(mberManageVO);
		return rs;
	}

	@Override
	public int userDelete(String userId) throws Exception  {
//		System.err.println("paramDTO.getUser_id() > " + userId);
//		String password=EgovFileScrty.encryptPassword(paramDTO.getUser_pwd(),paramDTO.getUser_id());
//		paramDTO.setUser_pwd(password);
//		MberManageVO mberManageVO =new MberManageVO();
//		mberManageVO.setMberId(paramDTO.getUser_id());
//		mberManageVO.setMberNm(paramDTO.getUser_name());
//		mberManageVO.setMberEmailAdres(paramDTO.getUser_email());
//		mberManageVO.setMoblphonNo(paramDTO.getUser_phone());
//		mberManageVO.setPassword(password);
//		mberManageVO.setPasswordHint("P00");
//		mberManageVO.setPasswordCnsr("none");
//		mberManageVO.setMberSttus("P");
//		//고유아이디 셋팅
//		String uniqId = idgenService.getNextStringId();
//		mberManageVO.setUniqId(uniqId);
//		
//		int rs=userService.create(paramDTO);
		String result = "";
				
		//LETTNGNRLMBER 삭제
		mberManageDAO.deleteMber(userId);
		
		//role 삭제
		userDao.deleteUserRole(userId);
		
		//tb_user 삭제
		return userService.delete(userId);
//		return rs;
	}
	
	/**
	 * 기 등록된 사용자 중 검색조건에 맞는 일반회원의 정보를 데이터베이스에서 읽어와 화면에 출력
	 * @param uniqId 상세조회대상 일반회원아이디
	 * @return mberManageVO 일반회원상세정보
	 * @throws Exception
	 */
	@Override
	public MberManageVO selectMber(String uniqId) {
		MberManageVO mberManageVO = mberManageDAO.selectMber(uniqId);
		return mberManageVO;
	}
	public MberManageVO selectMberByID(String mberId)  {
		MberManageVO mberManageVO = mberManageDAO.selectMberByID(mberId);
		return mberManageVO;
	}
	/**
	 * 기 등록된 회원 중 검색조건에 맞는 회원들의 정보를 데이터베이스에서 읽어와 화면에 출력
	 * @param userSearchVO 검색조건
	 * @return List<MberManageVO> 일반회원목록정보
	 */
	@Override
	public List<MberManageVO> selectMberList(UserDefaultVO userSearchVO) {
		int  pageNum=userSearchVO.getPageSize()==0?1:userSearchVO.getPageSize();
		 int rowSize=userSearchVO.getPageSize()==0?100:userSearchVO.getPageSize();
        userSearchVO.setFirstIndex((pageNum - 1) * rowSize );
        userSearchVO.setRecordCountPerPage(rowSize);
		return mberManageDAO.selectMberList(userSearchVO);
	}

    /**
     * 일반회원 총 갯수를 조회한다.
     * @param userSearchVO 검색조건
     * @return 일반회원총갯수(int)
     */
    @Override
	public int selectMberListTotCnt(UserDefaultVO userSearchVO) {
    	return mberManageDAO.selectMberListTotCnt(userSearchVO);
    }

	/**
	 * 화면에 조회된 일반회원의 기본정보를 수정하여 항목의 정합성을 체크하고 수정된 데이터를 데이터베이스에 반영
	 * @param mberManageVO 일반회원수정정보
	 * @throws Exception
	 */
	@Override
	public void updateMber(MberManageVO mberManageVO) throws Exception {
		//패스워드 암호화
		String pass = FileScrty.encryptPassword(mberManageVO.getPassword(), mberManageVO.getMberId());
		mberManageVO.setPassword(pass);
		mberManageDAO.updateMber(mberManageVO);
	}

	/**
	 * 화면에 조회된 사용자의 정보를 데이터베이스에서 삭제
	 * @param checkedIdForDel 삭제대상 일반회원아이디
	 * @throws Exception
	 */
	@Override
	public void deleteMber(String checkedIdForDel)  {
		String [] delId = checkedIdForDel.split(",");
		for (int i=0; i<delId.length ; i++){
			String [] id = delId[i].split(":");
			if (id[0].equals("USR03")){
		        //업무사용자(직원)삭제
			}else if(id[0].equals("USR01")){
				//일반회원삭제
				mberManageDAO.deleteMber(id[1]);
			}else if(id[0].equals("USR02")){
				//기업회원삭제
			}
		}
	}

	/**
	 * 일반회원 약관확인
	 * @param stplatId 일반회원약관아이디
	 * @return 일반회원약관정보(List)
	 * @throws Exception
	 */
	@Override
	public List<?> selectStplat(String stplatId)  {
        return mberManageDAO.selectStplat(stplatId);
	}

	/**
	 * 일반회원암호수정
	 * @param mberManageVO 일반회원수정정보(비밀번호)
	 * @throws Exception
	 */
	@Override
	public void updatePassword(MberManageVO mberManageVO) {
		mberManageDAO.updatePassword(mberManageVO);
	}

	/**
	 * 일반회원이 비밀번호를 기억하지 못할 때 비밀번호를 찾을 수 있도록 함
	 * @param passVO 일반회원암호 조회조건정보
	 * @return mberManageVO 일반회원암호정보
	 * @throws Exception
	 */
	@Override
	public MberManageVO selectPassword(MberManageVO passVO) {
		MberManageVO mberManageVO = mberManageDAO.selectPassword(passVO);
		return mberManageVO;
	}

	/**
	 * 입력한 사용자아이디의 중복여부를 체크하여 사용가능여부를 확인
	 * @param checkId 중복여부 확인대상 아이디
	 * @return 사용가능여부(아이디 사용회수 int)
	 * @throws Exception
	 */
	@Override
	public int checkIdDplct(String checkId) {
		return mberManageDAO.checkIdDplct(checkId);
	}

}