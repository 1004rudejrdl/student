/***
 *  내가 혼자 수정창 띄우는 작업한 소스 
 */

package Controller;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.time.LocalDate;
import java.util.ResourceBundle;

import Model.Student;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainController implements Initializable{

// ***************** 첫 번째 화면 시작 ************************
	 	
	@FXML private Button tab1BtnFirst;
			
	@FXML private ComboBox<String> t1CmbGrade; 
	@FXML private ComboBox<String> t1CmbBan;   
	@FXML private ToggleGroup group;

	@FXML private RadioButton t1RdoMale;  
	@FXML private RadioButton t1RdoFemale;

	@FXML private TextField t1TextName;
	@FXML private TextField t1TextNo;	
	@FXML private TextField t1TextLanguage;
	@FXML private TextField t1TextEnglish;
	@FXML private TextField t1TextMath;
	@FXML private TextField t1TextScience;
	@FXML private TextField t1TextSociety;
	@FXML private TextField t1TextMusic;
	@FXML private TextField t1TextSum;
	@FXML private TextField t1TextAvg;
	@FXML private TextField t1TextSearch;
		
	@FXML private Button t1BtnSum;
	@FXML private Button t1BtnAvg;
	@FXML private Button t1BtnReset;
	@FXML private Button t1BtnReg;
	@FXML private Button t1BtnEdit;  
	@FXML private Button t1BtnDel;   
	@FXML private Button t1BtnExit;
	@FXML private Button t1BtnSearch;
	@FXML private Button t1BtnBarChart; 
		
	@FXML private DatePicker t1DatePicker; 
	
	@FXML private TableView<Student> t1TableView;
	ObservableList<Student> t1ListData = FXCollections.observableArrayList();
	ObservableList<String> t1ListGrade = FXCollections.observableArrayList();
	ObservableList<String> t1ListBan = FXCollections.observableArrayList();
	private Student selectStudent; 
	private int selectStudentIndex;
	private boolean editFlag; // 디폴트 false -> 필요 없어졌지만, 테이블뷰 클릭시 등록 안되는거 없애기 위해 잠시 남김
	
// ***************** 두 번째 화면 시작 **************************
	@FXML private Button tab2BtnSecond;
// ***************** 세 번째 화면 시작 **************************	
	@FXML private Button tab3BtnThird;

	public Stage mainStage;


	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		tab1BtnFirst.setOnAction(event-> {handlerTab1BtnFirstAction();});
		tab2BtnSecond.setOnAction(event-> {handlerTab2BtnSecondAction();});
		tab3BtnThird.setOnAction(event-> {handlerTab3BtnThirdAction();}); // 3번텝 버튼 : 로그인화면으로 돌아가는 액션
		
		//1. 테이블뷰 기본셋팅
		setTab1TableView();
		
		//1-2. 콤보박스(학년, 반) 셋팅을 한다.
		setComboBoxGradeBan();
		
		//1-3. 점수 텍스트필드 포맷설정
		setTextFieldFormatSetting();
		
		
		//2. 버튼과 입력필드 초기설정
		setButtonTextFieldInitiate("처음");
		
		//3. 총점 버튼을 눌렀을 때 처리하는 함수
	    t1BtnSum.setOnAction(event -> {handleT1BtnSumAction();}); 
	//  t1BtnSum.setOnAction(event -> handleT1BtnSumAction()); 이렇게 해도 된다

		//4. 평균 버튼을 눌렀을 때 처리하는 함수
		t1BtnAvg.setOnAction(event -> {handleT1BtnAvgAction();});
		
		//5. 새로입력 버튼을 눌렀을 때 처리하는 함수
		t1BtnReset.setOnAction(event -> {handleT1BtnResetAction(0);});
		
		//6. 등록 버튼을 눌렀을 때 처리하는 함수
		t1BtnReg.setOnAction(event -> {handleT1BtnRegAction();});
		
		//7. 종료 버튼 눌렀을 때 처리하는 함수
		t1BtnExit.setOnAction(event -> {Platform.exit();});
		
		//8. 테이블뷰를 눌렀을 때 처리하는 함수 (한번클릭)
		t1TableView.setOnMouseClicked(event-> {handleT1TableViewAction(event);});
				
		//9. 수정버튼 눌렀을 때 처리하는 함수
		t1BtnEdit.setOnAction(event-> {handleT1BtnEditAction();});
		
		//10. 삭제버튼 눌렀을 때 처리하는 함수
		t1BtnDel.setOnAction(event-> {handleT1BtnDelAction();});
		
		//11. 검색버튼 눌렀을 때 처리하는 함수
		t1BtnSearch.setOnAction(event-> {handleT1BtnSearchAction();});
		
		//12. 바차트 버튼을 눌렀을 때 처리하는 함수 (새로운 스테이지로 바차트 보여줌)
		t1BtnBarChart.setOnAction(event-> {handleT1BtnBarChartAction();});
		
		//13. 수정 버튼을 눌렀을 때 처리하는 함수 (새로운 스테이지로 바차트 보여줌)
		t1BtnEdit.setOnAction(event-> {handleT1BtnEditAction();});
		
	}
	
	//1. 테이블뷰 기본셋팅
	private void setTab1TableView() {

		TableColumn tcNo = t1TableView.getColumns().get(0);                 // 5단계 추가
		tcNo.setCellValueFactory(new PropertyValueFactory<>("no"));    
		tcNo.setStyle("-fx-alignment: CENTER;");
		
		TableColumn tcName = t1TableView.getColumns().get(1);
		tcName.setCellValueFactory(new PropertyValueFactory<>("name"));
		tcName.setStyle("-fx-alignment: CENTER;");

		TableColumn tcGrade = t1TableView.getColumns().get(2);
		tcGrade.setCellValueFactory(new PropertyValueFactory<>("grade"));
		tcGrade.setStyle("-fx-alignment: CENTER;");
		
		TableColumn tcBan = t1TableView.getColumns().get(3);
		tcBan.setCellValueFactory(new PropertyValueFactory<>("ban"));
		tcBan.setStyle("-fx-alignment: CENTER;");

		TableColumn tcGender = t1TableView.getColumns().get(4);
		tcGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
		tcGender.setStyle("-fx-alignment: CENTER;");
		
		TableColumn tcLanguage = t1TableView.getColumns().get(5);
		tcLanguage.setCellValueFactory(new PropertyValueFactory<>("language"));
		tcLanguage.setStyle("-fx-alignment: CENTER;");

		TableColumn tcEnglish = t1TableView.getColumns().get(6);
		tcEnglish.setCellValueFactory(new PropertyValueFactory<>("english"));
		tcEnglish.setStyle("-fx-alignment: CENTER;");

		TableColumn tcMath = t1TableView.getColumns().get(7);
		tcMath.setCellValueFactory(new PropertyValueFactory<>("math"));
		tcMath.setStyle("-fx-alignment: CENTER;");

		TableColumn tcScience = t1TableView.getColumns().get(8);
		tcScience.setCellValueFactory(new PropertyValueFactory<>("science"));
		tcScience.setStyle("-fx-alignment: CENTER;");

		TableColumn tcSociety = t1TableView.getColumns().get(9);
		tcSociety.setCellValueFactory(new PropertyValueFactory<>("society"));
		tcSociety.setStyle("-fx-alignment: CENTER;");

		TableColumn tcMusic = t1TableView.getColumns().get(10);
		tcMusic.setCellValueFactory(new PropertyValueFactory<>("music"));
		tcMusic.setStyle("-fx-alignment: CENTER;");

		TableColumn tcSum = t1TableView.getColumns().get(11);
		tcSum .setCellValueFactory(new PropertyValueFactory<>("sum"));
		tcSum .setStyle("-fx-alignment: CENTER;");

		TableColumn tcAvg = t1TableView.getColumns().get(12);
		tcAvg.setCellValueFactory(new PropertyValueFactory<>("avg"));
		tcAvg.setStyle("-fx-alignment: CENTER;");
		
		TableColumn tcDate = t1TableView.getColumns().get(13);
		tcDate.setCellValueFactory(new PropertyValueFactory<>("date"));
		tcDate.setStyle("-fx-alignment: CENTER;");
		
//		t1ListData.add(new Student("Rok","3","4","99","99","99","99","99","99","594","99"));
		
		t1TableView.setItems(t1ListData);
		
	}

	//1-2. 콤보박스(학년, 반) 셋팅을 한다.
	private void setComboBoxGradeBan() {
		t1ListGrade.addAll("1학년","2학년","3학년","4학년","5학년","6학년");
		t1ListBan.addAll("1반","2반","3반","4반","5반","6반");
		t1CmbGrade.setItems(t1ListGrade);
		t1CmbBan.setItems(t1ListBan);
	}
	
	//1-3. 점수 텍스트필드 포맷설정
	private void setTextFieldFormatSetting() {
		inputDecimalFormatTwoDgit(t1TextNo); // 2자리
		inputDecimalFormat(t1TextLanguage);
		inputDecimalFormat(t1TextEnglish);
		inputDecimalFormat(t1TextMath);
		inputDecimalFormat(t1TextScience);
		inputDecimalFormat(t1TextSociety);
		inputDecimalFormat(t1TextMusic);
	}
		
	//2. 버튼과 입력필드 초기설정 
	private void setButtonTextFieldInitiate(String message) {
		t1DatePicker.setEditable(false);
		t1TextSum.setEditable(false);
		t1TextAvg.setEditable(false);
		switch(message) {
		case "처음" : 
		case "새로입력" :			
			t1BtnSum.setDisable(false);
			t1BtnAvg.setDisable(true);			
			t1BtnReg.setDisable(true);
			t1BtnEdit.setDisable(true);
			t1BtnDel.setDisable(true);
			
			t1CmbGrade.setDisable(false);
			t1CmbBan.setDisable(false);		
			t1DatePicker.setDisable(false);
			t1TextNo.setDisable(false);
			t1TextName.setDisable(false);
			t1TextLanguage.setDisable(false);
			t1TextEnglish.setDisable(false);
			t1TextMath.setDisable(false);
			t1TextScience.setDisable(false);
			t1TextSociety.setDisable(false);
			t1TextMusic.setDisable(false);
			t1TextSum.setDisable(false);
			t1TextAvg.setDisable(false);
			t1TableView.getSelectionModel().clearSelection(); // 테이블뷰 선택 없애기
			
			break;
		case "총점" :
			t1BtnSum.setDisable(true);
			t1BtnAvg.setDisable(false);
			t1BtnReg.setDisable(true);
			t1BtnEdit.setDisable(true);
			t1BtnDel.setDisable(true);
			break;
		case "평균" :
			t1BtnSum.setDisable(true);
			t1BtnAvg.setDisable(true);
			t1BtnReg.setDisable(false);
			t1BtnEdit.setDisable(true);
//			if(editFlag) {
//				t1BtnReg.setDisable(true);
//				t1BtnEdit.setDisable(false);
//				editFlag = false;				
//			}else {
//				t1BtnReg.setDisable(false);
//			}
			break;
		case "테이블뷰클릭" :
			t1BtnSum.setDisable(true);
			t1BtnAvg.setDisable(true);			
			t1BtnReg.setDisable(true);			
			t1BtnEdit.setDisable(false);			
			t1BtnDel.setDisable(false);
			
			t1CmbGrade.setDisable(true);
			t1CmbBan.setDisable(true);
			
			t1DatePicker.setDisable(true);
			
			
			
			// 콤보박스랑 데이터픽커는 setEditable 안되네 Disable로 하자 
			// 생각해보니 나머지도 Disable로 해야겠다
//			t1TextNo.setEditable(false);
//			t1TextName.setEditable(false);						
//			t1TextLanguage.setEditable(false);
//			t1TextEnglish.setEditable(false);
//			t1TextMath.setEditable(false);
//			t1TextScience.setEditable(false);
//			t1TextSociety.setEditable(false);
//			t1TextMusic.setEditable(false);
			
			t1TextNo.setDisable(true);
			t1TextName.setDisable(true);
			t1TextLanguage.setDisable(true);
			t1TextEnglish.setDisable(true);
			t1TextMath.setDisable(true);
			t1TextScience.setDisable(true);
			t1TextSociety.setDisable(true);
			t1TextMusic.setDisable(true);
			
			// 총점, 평균 버튼도 
			t1TextSum.setDisable(true);
			t1TextAvg.setDisable(true);
			
			break;
		}		
	}

	//3. 총점 버튼을 눌렀을 때 처리하는 함수	
	private void handleT1BtnSumAction() {
		int language = Integer.parseInt(t1TextLanguage.getText()); // 아 내 과제에도 이거 적용하자
		int english = Integer.parseInt(t1TextEnglish.getText());
		int math = Integer.parseInt(t1TextMath.getText());
		int science = Integer.parseInt(t1TextScience.getText());
		int society = Integer.parseInt(t1TextSociety.getText());
		int music = Integer.parseInt(t1TextMusic.getText());
	
		String message ="점수입력 :";
		if(language>100) {
			message += ("language ="+language+" ");			
		}
		if(english>100) {
			message += ("english ="+english+" ");			
		}
		if(math>100) {
			message += ("math ="+math+" ");			
		}
		if(science>100) {
			message += ("science ="+science+" ");			
		}
		if(society>100) {
			message += ("society ="+society+" ");			
		}
		if(music>100) {
			message += ("music ="+music+" ");			
		}
		
		if(!message.equals("점수입력 :")) {
			message +="점수를 잘 입력해주세요.";
			callAlert(message);
			return;
		}
		
		t1TextSum.setText(String.valueOf(language+english+math+science+society+music));

/*		String str = ""+(
		Integer.parseInt(t1TextLanguage.getText())+
		Integer.parseInt(t1TextEnglish.getText())+
		Integer.parseInt(t1TextMath.getText())+
		Integer.parseInt(t1TextScience.getText())+
		Integer.parseInt(t1TextSociety.getText())+
		Integer.parseInt(t1TextMusic.getText())
		);
		t1TextSum.setText(str);
		
		혹은
		
		t1TextSum.setText( ""+( 
		Integer.parseInt(t1TextLanguage.getText())+
		Integer.parseInt(t1TextEnglish.getText())+
		Integer.parseInt(t1TextMath.getText())+
		Integer.parseInt(t1TextScience.getText())+
		Integer.parseInt(t1TextSociety.getText())+
		Integer.parseInt(t1TextMusic.getText())
		));		
	    
	    이런식으로도 가능하다. 하나가 문자면("") 전부다 문자로 되고
	    애초에 int가 아니니까 String.valueOf를 안해도 된다
		
	    근데 점수 범위 확인 및 간결하게 하기 위해 원래 방식이 나은 것 같다*/

		setButtonTextFieldInitiate("총점");
		
	}

	//4. 평균 버튼을 눌렀을 때 처리하는 함수
	private void handleT1BtnAvgAction() {
		t1TextAvg.setText(String.format("%.2f",Double.parseDouble(t1TextSum.getText())/6));
		setButtonTextFieldInitiate("평균");		
	}
	
	//5. 새로입력 버튼을 눌렀을 때 처리하는 함수
	private void handleT1BtnResetAction(int a) { // 새로입력랑 등록이랑 구분하는 매개변수
		if (a == 0) {
			t1DatePicker.setValue(null);
			t1CmbGrade.getSelectionModel().clearSelection();
			t1CmbBan.getSelectionModel().clearSelection();
		} 
		t1TextName.clear();
		t1TextNo.clear();
		group.selectToggle(null);
		t1TextLanguage.clear();
		t1TextEnglish.clear();
		t1TextMath.clear();
		t1TextScience.clear();
		t1TextSociety.clear();
		t1TextMusic.clear();
		t1TextSum.clear();
		t1TextAvg.clear();   // alt + shift + a
		
		setButtonTextFieldInitiate("새로입력"); // "처음"과 동일
	}

	//6. 등록 버튼을 눌렀을 때 처리하는 함수
	private void handleT1BtnRegAction() {
		String studentNo ="";

		String no="0";
		String ban="0"; 
		
		
		if (t1TextNo.getText().trim().length()==1) {
			no+=t1TextNo.getText().trim();
		}else {
			no =t1TextNo.getText().trim();
		}
		
		if (t1CmbBan.getSelectionModel().getSelectedItem().substring(0, 1).trim().length()==1) {
			ban += t1CmbBan.getSelectionModel().getSelectedItem().substring(0, 1).trim();
			// (0,1)이면 앞글자 첫자리만 따라는 뜻
		} else {
			ban = t1CmbBan.getSelectionModel().getSelectedItem().substring(0, 1).trim();
		}
		
		studentNo = t1CmbGrade.getSelectionModel().getSelectedItem().substring(0, 1).trim() + ban + no ;
				
		// Student student = new Student( ~ )
		// t1ListData.add(student)   이런 식으로 해도 되는데 한줄로 한거다
		// 저 속의 내용을 여러번 써야한다면 두줄로 하는게 좋다
		
		t1ListData.add(new Student(
							studentNo, // Student no 변수에 studentNo값(다섯자리 값)이 들어간다
							t1TextName.getText(),
							t1CmbGrade.getSelectionModel().getSelectedItem(),
							t1CmbBan.getSelectionModel().getSelectedItem(),
							group.getSelectedToggle().getUserData().toString(),
							t1TextLanguage.getText(),
							t1TextEnglish.getText(),
							t1TextMath.getText(),
							t1TextScience.getText(),
							t1TextSociety.getText(),
							t1TextMusic.getText(),
							t1TextSum.getText(),
							t1TextAvg.getText(),
							t1DatePicker.getValue().toString()
	));    
	
		handleT1BtnResetAction(1); // 새로입력 버튼 함수 : 0 넣으면 전체 초기화
		                           // 나머지 숫자는 날짜+학년+반 초기화 x
	}
	
	//7. 종료 버튼 눌렀을 때 처리하는 함수 (라이브러리)

	//8. 테이블뷰를 클릭했을 때 처리하는 함수 (원클릭->수정, 더블클릭->파이차트) 
	private void handleT1TableViewAction(MouseEvent event) {
		// 원클릭 - 수정 ->
		
		selectStudent = t1TableView.getSelectionModel().getSelectedItem(); 
		// 이게 이해 안되는게 Student 타입인데, 객체를 만든 것이 아닌데 getName()도 쓰고 다 하네?
		// New로 객체를 만드는건 그 클래스의 모든 정보를 담아오는거고
		// 이건 그 클래스의 것들을 쓰겠다는 그런 뜻인가..?
		
		// t1TableView도 <Student>니까 이것도 Student 클래스로 하는건가보다
		                     
		selectStudentIndex = t1TableView.getSelectionModel().getSelectedIndex();
		if(event.getClickCount() == 1) {
			
			t1DatePicker.setValue(LocalDate.parse(selectStudent.getDate()));
			// parse 명령어 잘 모르겠다
			
			t1TextNo.setText(selectStudent.getNo().substring(3)); 
			// setText 이후 스트링값와야하고 Student 클래스에서 getNo()로 
			// Student 클래스의 멤버변수 no의 스트링값을 가져오는거고, 거기서 substring으로 앞 3자리를 자르는 것
			// substring으로 (2, 4) 이렇게 쓰면 2번째자리까지 자르고, 4번째자리까지 보여줌 
			// ex) abcdefg -> cd
			// 근데 여기서 substring(2)로 하면 왜 아무것도 안뜨지..? 10221이면 221이 되어야 하는데
			// substring(2,3) 하면 10221이면 2가 잘 뜨는데 왜 (2)로 하면 아무것도 안뜨지
			
			t1TextName.setText(selectStudent.getName());
			t1CmbGrade.getSelectionModel().select(selectStudent.getGrade());
			t1CmbBan.getSelectionModel().select(selectStudent.getBan());		
			
			if(selectStudent.getGender().equals("남성")) {
				t1RdoMale.setSelected(true);
			}else {
				t1RdoFemale.setSelected(true);
			}
			
			t1TextLanguage.setText(selectStudent.getLanguage());
			t1TextEnglish.setText(selectStudent.getEnglish());
			t1TextMath.setText(selectStudent.getMath());
			t1TextScience.setText(selectStudent.getScience());
			t1TextSociety.setText(selectStudent.getSociety());
			t1TextMusic.setText(selectStudent.getMusic());
			t1TextSum.setText(selectStudent.getSum());
			t1TextAvg.setText(selectStudent.getAvg());
			
//			editFlag = true; // 플래그 기능 중요 
//			// true이기 때문에 평균버튼 누르면 수정 버튼 활성화 됨
			
			setButtonTextFieldInitiate("테이블뷰클릭"); 
		// 더블클릭 - 파이차트 
		}else if (event.getClickCount() == 2) {
		   	try {
				Stage pieStage = new Stage(StageStyle.UTILITY);
				pieStage.initModality(Modality.WINDOW_MODAL);
				pieStage.initOwner(mainStage);
				pieStage.setTitle(selectStudent.getName()+"'s PieChart");
				FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/piechart.fxml"));
				// 절대경로 (C:부터 쭉 쓰는거), 상대경로 (자기 자신으로부터 쓰는거 - ../ 쓰면 위로 올라가는 것 ../../../ 세번 올라가는 것 ./ 자기자신)
				Parent root = loader.load();
				
				//**********아이디 찾아오기 = @FXML private Button c1BtnExit**********
				PieChart c2PieChart = (PieChart) root.lookup("#c2PieChart");
				Button c2BtnExit = (Button) root.lookup("#c2BtnExit");

				//*********************이벤트 등록 및 초기화, 핸들러 정의*************
				// 파이차트 초기화 작업
				ObservableList pieList = FXCollections.observableArrayList();
				pieList.add(new PieChart.Data("국어", Integer.parseInt(selectStudent.getLanguage())));
				pieList.add(new PieChart.Data("영어", Integer.parseInt(selectStudent.getEnglish())));
				pieList.add(new PieChart.Data("수학", Integer.parseInt(selectStudent.getMath())));
				pieList.add(new PieChart.Data("과학", Integer.parseInt(selectStudent.getScience())));
				pieList.add(new PieChart.Data("사회", Integer.parseInt(selectStudent.getSociety())));
				pieList.add(new PieChart.Data("음악", Integer.parseInt(selectStudent.getMusic())));
								
				c2PieChart.setData(pieList);
				//******************************************************************** 
				c2BtnExit.setOnAction(e -> {pieStage.close();});
				//********************************************************************
				Scene scene = new Scene(root);
				pieStage.setScene(scene);		
				pieStage.show();				
				
			} catch (IOException e) {
				callAlert("파이차트 창 오류 : 바차트 창 오류가 발생했습니다.");
			}			
						
		}else {
			return;
		}
 
	}
	

	   //9. 수정버튼 눌렀을 때 처리하는 함수 (새로운 스테이지)
	private void handleT1BtnEditAction() {
		ObservableList<String> editListGrade = FXCollections.observableArrayList();
		ObservableList<String> editListBan = FXCollections.observableArrayList();
		ObservableList<String> editListGender = FXCollections.observableArrayList();
		
		try {
			Stage editStage = new Stage(StageStyle.UTILITY);
			editStage.initModality(Modality.WINDOW_MODAL);
			editStage.initOwner(mainStage);
			editStage.setTitle(selectStudent.getName() + "'s Edit Window");
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/edit.fxml"));
			Parent root = loader.load();
			// ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
			
			// ***********아이디 찾아오기****************************************
			TextField editTxtNo = (TextField) root.lookup("#editTxtNo");
			TextField editTxtName = (TextField) root.lookup("#editTxtName");
			TextField editTxtLanguage = (TextField) root.lookup("#editTxtLanguage");
			TextField editTxtEnglish = (TextField) root.lookup("#editTxtEnglish");
			TextField editTxtMath = (TextField) root.lookup("#editTxtMath");
			TextField editTxtScience = (TextField) root.lookup("#editTxtScience");
			TextField editTxtSociety = (TextField) root.lookup("#editTxtSociety");
			TextField editTxtMusic = (TextField) root.lookup("#editTxtMusic");
			TextField editTxtSum = (TextField) root.lookup("#editTxtSum");
			TextField editTxtAvg = (TextField) root.lookup("#editTxtAvg");
						
			ComboBox<String> editCmbGrade = (ComboBox) root.lookup("#editCmbGrade");
			ComboBox<String> editCmbBan = (ComboBox) root.lookup("#editCmbBan");
			ComboBox<String> editCmbGender = (ComboBox) root.lookup("#editCmbGender");
			
			Button editBtnCal = (Button) root.lookup("#editBtnCal");
			Button editBtnSave = (Button) root.lookup("#editBtnSave");
			Button editBtnCancel = (Button) root.lookup("#editBtnCancel");
			
			DatePicker editDatePicker = (DatePicker) root.lookup("#editDatePicker");
	
			
			// ***** 콤보박스 셋팅 (학년, 반, 성별) **********
			editListGrade.addAll("1학년", "2학년", "3학년", "4학년", "5학년", "6학년");
			editListBan.addAll("1반", "2반", "3반", "4반", "5반", "6반");
			editListGender.addAll("남성", "여성");
			editCmbGrade.setItems(editListGrade);
			editCmbBan.setItems(editListBan);
			editCmbGender.setItems(editListGender);
			
			
			//******* 점수를 바꾸면 저장버튼 비활성화 되고, 계산버튼 눌러야 활성화되게 하기 ***********  
			//        점수칸을 누르기만하면 비활성화 되게 하든지 
			// => 현재 : 계산 안누르고 저장 눌러도 저장되게 되어있는 오류 있다                              #############################
			
			
//			 if (t1TextLanguage != editTxtLanguage)

			
			
			//******* 총점이랑, 평균은 임의로 바꿀 수 없고 계산으로만 하게 막아두기 *******************
			
//			editTxtSum.setDisable(true);
//			editTxtAvg.setDisable(true);
			
			editTxtSum.setEditable(false);
			editTxtAvg.setEditable(false);
						
			// 프라이머리 키를 바꿀 수 없으니까 학년 반 번호도 못바꾸게 하긴 해야하는데
			// 학년 올라가면..? 그래서 대학교 학번처럼 쭉 가는걸로 해야 맞긴하네..
			// 프라이머리키를... 등록년도+등록일+ ... 음... 사이트들 회원번호 어떻게 부여할까?
			
			// 일단, 데이터베이스와 연계를 위해서 학년, 반, 번호도 못바꾸게 해두자
			
			editCmbGrade.setDisable(true); // 위에 콤보박스 셋팅 괜히했네 텍스트로 해도 되는데
			editCmbBan.setDisable(true);			
			editTxtNo.setDisable(true);
			
			// DatePicker 직접입력 못하게하고 선택하게 함
			editDatePicker.setEditable(false);
			
			//**********************값 자동 셋팅 **********************************
			
			selectStudent = t1TableView.getSelectionModel().getSelectedItem();
			selectStudentIndex = t1TableView.getSelectionModel().getSelectedIndex(); 
			// 나는 이게 좀 햇깔리네 이거 왜 하는건지 그리고 등록할 땐 이거 없는데 수정할 땐 이거 있고
			
			editDatePicker.setValue(LocalDate.parse(selectStudent.getDate()));
			
			editTxtNo.setText(selectStudent.getNo().substring(3));
			editTxtName.setText(selectStudent.getName());

			editCmbGrade.getSelectionModel().select(selectStudent.getGrade());
			editCmbBan.getSelectionModel().select(selectStudent.getBan());
			editCmbGender.getSelectionModel().select(selectStudent.getGender());
			
//			if(selectStudent.getGender().equals("남성")) {
//				t1RdoMale.setSelected(true);
//			}else {
//				t1RdoFemale.setSelected(true);
//			}

			editTxtLanguage.setText(selectStudent.getLanguage());
			editTxtEnglish.setText(selectStudent.getEnglish());
			editTxtMath.setText(selectStudent.getMath());
			editTxtScience.setText(selectStudent.getScience());
			editTxtSociety.setText(selectStudent.getSociety());
			editTxtMusic.setText(selectStudent.getMusic());
			editTxtSum.setText(selectStudent.getSum());
			editTxtAvg.setText(selectStudent.getAvg());

			editTxtSum.setEditable(false);
			editTxtAvg.setEditable(false);

			//******* 계산 버튼을 눌렀을 때 총점과 평균 계산
			editBtnCal.setOnAction(event -> {
				int language = Integer.parseInt(editTxtLanguage.getText()); // 아 내 과제에도 이거 적용하자
				int english = Integer.parseInt(editTxtEnglish.getText());
				int math = Integer.parseInt(editTxtMath.getText());
				int science = Integer.parseInt(editTxtScience.getText());
				int society = Integer.parseInt(editTxtSociety.getText());
				int music = Integer.parseInt(editTxtMusic.getText());
			
				String message ="점수입력 :";
				if(language>100) {
					message += ("language ="+language+" ");			
				}
				if(english>100) {
					message += ("english ="+english+" ");			
				}
				if(math>100) {
					message += ("math ="+math+" ");			
				}
				if(science>100) {
					message += ("science ="+science+" ");			
				}
				if(society>100) {
					message += ("society ="+society+" ");			
				}
				if(music>100) {
					message += ("music ="+music+" ");			
				}				
				if(!message.equals("점수입력 :")) {
					message +="점수를 잘 입력해주세요.";
					callAlert(message);
					return;
				}
				
				
				editTxtSum.setText(String.valueOf(language+english+math+science+society+music));
				editTxtAvg.setText(String.format("%.2f", Double.parseDouble(editTxtSum.getText())/6));
								
			});
			
			//**************** 저장 버튼 눌렀을 때 수정하고 창 닫히고 메인 입력창 초기화 ********
			editBtnSave.setOnAction(event -> {
				
				t1ListData.remove(selectStudentIndex);
				// 여기는 왜 selecStudentIndex가 있는지 모르겠네 add 함수 오버로딩인 찾아보자
			
				String studentNo = "";

				String no = "0";
				String ban = "0";

				if (editTxtNo.getText().trim().length() == 1) {
					no += editTxtNo.getText().trim();
				} else {
					no = editTxtNo.getText().trim();
				}

				if (editCmbBan.getSelectionModel().getSelectedItem().substring(0, 1).trim().length() == 1) {
					ban += editCmbBan.getSelectionModel().getSelectedItem().substring(0, 1).trim();
					// (0,1)이면 앞글자 첫자리만 따라는 뜻
				} else {
					ban = editCmbBan.getSelectionModel().getSelectedItem().substring(0, 1).trim();
				}

				studentNo = editCmbGrade.getSelectionModel().getSelectedItem().substring(0, 1).trim() + ban + no;

		
				t1ListData.add(selectStudentIndex, new Student(
						studentNo, // Student no 변수에 studentNo값(다섯자리 값)이 들어간다
						editTxtName.getText(), 
						editCmbGrade.getSelectionModel().getSelectedItem(),
						editCmbBan.getSelectionModel().getSelectedItem(),
						editCmbGender.getSelectionModel().getSelectedItem(),
						editTxtLanguage.getText(),
						editTxtEnglish.getText(), 
						editTxtMath.getText(), 
						editTxtScience.getText(), 
						editTxtSociety.getText(),
						editTxtMusic.getText(), 
						editTxtSum.getText(), 
						editTxtAvg.getText(),
						editDatePicker.getValue().toString()));

				handleT1BtnResetAction(0);
				callAlert("수정완료 : " + selectStudent.getName() + "님의 수정이 완료되었습니다");
				editStage.close();
				// Platform.exit(); 이거하면, 전체가 닫힌다
			}); 
			
			
			
			//**************** 취소 버튼 눌렀을 때 창 닫기 ***********************
			editBtnCancel.setOnAction(event -> {editStage.close();});			
			
 
			
			// ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
			Scene scene = new Scene(root); 
			editStage.setScene(scene);     
			editStage.show();

		} catch (IOException e) {
			callAlert("수정창 오류 : 수정창 오류가 발생했습니다.");
		}

	}
	   
	//10. 삭제버튼 눌렀을 때 처리하는 함수
	private void handleT1BtnDelAction() {
		t1ListData.remove(selectStudentIndex);
		editFlag = false; // 삭제하고나서 테이블뷰 눌러있어서 등록 안뜨니까 이거 넣어줌
		
		// 새로입력 버튼을 눌렀을 때의 함수 콜
		handleT1BtnResetAction(0);
		callAlert("삭제완료 : "+selectStudent.getName() +"님이 삭제되었습니다");
	}

	//11. 검색버튼 눌렀을 때 처리하는 함수
	private void handleT1BtnSearchAction() {
		for (Student student :t1ListData) {
			if(t1TextSearch.getText().trim().equals(student.getName())) {
				t1TableView.getSelectionModel().select(student);
			}
		} // end of for each
	}

	//12. 바차트 버튼을 눌렀을 때 처리하는 함수 (새로운 스테이지로 바차트 보여줌)
	private void handleT1BtnBarChartAction() {
		try {
			Stage bcStage = new Stage(StageStyle.UTILITY);
			bcStage.initModality(Modality.WINDOW_MODAL);
			bcStage.initOwner(mainStage);			
			bcStage.setTitle("Total Student BarChart");
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/barchart.fxml"));
			Parent root = loader.load();
			//**********아이디 찾아오기 = @FXML private Button c1BtnExit**********
			BarChart c1BarChart = (BarChart) root.lookup("#c1BarChart");
			Button c1BtnExit = (Button) root.lookup("#c1BtnExit");
			//*********************이벤트 등록 및 초기화, 핸들러 정의*************
			// 바차트 초기화 작업
			
			// 국어
			XYChart.Series seriesLanguage = new XYChart.Series<>();
			seriesLanguage.setName("국어");
			ObservableList languageList = FXCollections.observableArrayList();
			for (int i=0; i<t1ListData.size(); i++) {
				languageList.add(new XYChart.Data<>(t1ListData.get(i).getName(), Integer.parseInt(t1ListData.get(i).getLanguage())));
			}
			seriesLanguage.setData(languageList);
			c1BarChart.getData().add(seriesLanguage);
	
			
			// 영어
			XYChart.Series seriesEnglish = new XYChart.Series<>();
			seriesEnglish.setName("영어");
			ObservableList englishList = FXCollections.observableArrayList();
			for (int i=0; i<t1ListData.size(); i++) {
				englishList.add(new XYChart.Data<>(t1ListData.get(i).getName(), Integer.parseInt(t1ListData.get(i).getEnglish())));
			}
			seriesEnglish.setData(englishList);
			c1BarChart.getData().add(seriesEnglish);
			
			// 수학
			XYChart.Series seriesMath = new XYChart.Series<>();
			seriesMath.setName("수학");
			ObservableList mathList = FXCollections.observableArrayList();
			for (int i=0; i<t1ListData.size(); i++) {
				mathList.add(new XYChart.Data<>(t1ListData.get(i).getName(), Integer.parseInt(t1ListData.get(i).getMath())));
			}
			seriesMath.setData(mathList);
			c1BarChart.getData().add(seriesMath);
			
			// 과학
			XYChart.Series seriesScience = new XYChart.Series<>();
			seriesScience.setName("과학");
			ObservableList scienceList = FXCollections.observableArrayList();
			for (int i=0; i<t1ListData.size(); i++) {
				scienceList.add(new XYChart.Data<>(t1ListData.get(i).getName(), Integer.parseInt(t1ListData.get(i).getScience())));
			}
			seriesScience.setData(scienceList);
			c1BarChart.getData().add(seriesScience);
			
			// 사회
			XYChart.Series seriesSociety = new XYChart.Series<>();
			seriesSociety.setName("과학");
			ObservableList societyList = FXCollections.observableArrayList();
			for (int i=0; i<t1ListData.size(); i++) {
				societyList.add(new XYChart.Data<>(t1ListData.get(i).getName(), Integer.parseInt(t1ListData.get(i).getSociety())));
			}
			seriesSociety.setData(societyList);
			c1BarChart.getData().add(seriesSociety);
			
			// 음악
			XYChart.Series seriesMusic = new XYChart.Series<>();
			seriesMusic.setName("과학");
			ObservableList musicList = FXCollections.observableArrayList();
			for (int i=0; i<t1ListData.size(); i++) {
				musicList.add(new XYChart.Data<>(t1ListData.get(i).getName(), Integer.parseInt(t1ListData.get(i).getMusic())));
			}
			seriesMusic.setData(musicList);
			c1BarChart.getData().add(seriesMusic);

			//********************************************************************
			
			c1BtnExit.setOnAction((e)->{bcStage.close();});
			
			
			
			//******************************************************************** 
			Scene scene = new Scene(root);
			bcStage.setScene(scene);		
			bcStage.show();
			
			
		} catch (IOException e) {
			callAlert("바차트 창 오류 : 바차트 창 오류가 발생했습니다.");
		}
		
		// 절대경로 (C:부터 쭉 쓰는거), 상대경로 (자기 자신으로부터 쓰는거 - ../ 쓰면 위로 올라가는 것 ../../../ 세번 올라가는 것 ./ 자기자신)
	}
	
	public void handlerTab1BtnFirstAction(){
		callAlert("알림창 : 첫번째 화면입니다.");
	}
	
	private void handlerTab2BtnSecondAction() {
		callAlert("알림창 : 두번째 화면입니다.");
	}
	
	// 탭 3번버튼 다시 로그인화면으로 가는 액션
	private void handlerTab3BtnThirdAction() {
		try {
			Stage primaryStage = new Stage();
			FXMLLoader loader=new FXMLLoader(getClass().getResource("../View/login.fxml"));
			Parent root = loader.load();
			
			RootController rootController = loader.getController();
			rootController.primaryStage=primaryStage; // 화면 체인지 추가
			
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("../application/add.css").toString());
			primaryStage.setScene(scene);
			mainStage.close(); //기존 메인화면 닫기
			primaryStage.show();
			
			callAlert("화면전환성공 : 메인화면으로 전환되었습니다.");
		}catch (IOException e) {
			callAlert("화면전환오류 : 화면 전환에 문제가 있습니다.");
		}
	}
	// 기타 : 알림창 = "오류정보 : 값을 제대로 입력해주세요" (꼭 콜론을 적어줄것)
	public static void callAlert(String contentText) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("알림창");
		alert.setHeaderText(contentText.substring(0, contentText.lastIndexOf(":")));
		alert.setContentText(contentText.substring(contentText.lastIndexOf(":")+1));
		alert.showAndWait();
	}

	// 기타 : 텍스트필드 포맷설정 - 숫자 세자리만 받는 셋팅
	private void inputDecimalFormat(TextField textField) {
		
		// 숫자만 입력(정수만 입력받음), 실수입력받고싶으면 new DecimalFormat("###.#");
		DecimalFormat format = new DecimalFormat("###");
		// 점수 입력시 길이 제한 이벤트 처리
		textField.setTextFormatter(new TextFormatter<>(event -> {
		
			//입력받은 내용이 없으면 이벤트를 리턴함.  
		if (event.getControlNewText().isEmpty()) { return event; }
		//구문을 분석할 시작 위치를 지정함. 
		    	ParsePosition parsePosition = new ParsePosition(0);
			//입력받은 내용과 분석위치를 지정한지점부터 format 내용과 일치한지 분석함.
		    	Object object = format.parse(event.getControlNewText(), parsePosition); 
		//리턴값이 null 이거나, 입력한길이와 구문분석위치값이 적어버리면(다 분석하지못했음을 뜻함) 거나, 입력한길이가 4이면(3자리를 넘었음을 뜻함.) 이면 null 리턴함. 
		if (object == null || parsePosition.getIndex()<event.getControlNewText().length()
		      || event.getControlNewText().length() == 4) {
		     return null;    
		}else {
		     return event;    
		}   
		}));
				
	}
	
	// 기타 : 텍스트필드 포맷설정 - 숫자 두자리만 받는 셋팅
	private void inputDecimalFormatTwoDgit(TextField textField) {
			
		// 숫자만 입력(정수만 입력받음), 실수입력받고싶으면 new DecimalFormat("###.#");
		DecimalFormat format = new DecimalFormat("##");
		// 점수 입력시 길이 제한 이벤트 처리
		textField.setTextFormatter(new TextFormatter<>(event -> {
			
		//입력받은 내용이 없으면 이벤트를 리턴함.  
		if (event.getControlNewText().isEmpty()) { return event; }
			//구문을 분석할 시작 위치를 지정함. 
		    ParsePosition parsePosition = new ParsePosition(0);
			//입력받은 내용과 분석위치를 지정한지점부터 format 내용과 일치한지 분석함.
			Object object = format.parse(event.getControlNewText(), parsePosition); 
			//리턴값이 null 이거나, 입력한길이와 구문분석위치값이 적어버리면(다 분석하지못했음을 뜻함) 거나, 입력한길이가 4이면(3자리를 넘었음을 뜻함.) 이면 null 리턴함. 
			if (object == null || parsePosition.getIndex()<event.getControlNewText().length()
			      || event.getControlNewText().length() == 3) {
			     return null;    
			}else {
			     return event;    
			}   
			}));			
		}
	
}
