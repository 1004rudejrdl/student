/***
 *  ���� ȥ�� ����â ���� �۾��� �ҽ� 
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

// ***************** ù ��° ȭ�� ���� ************************
	 	
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
	private boolean editFlag; // ����Ʈ false -> �ʿ� ����������, ���̺�� Ŭ���� ��� �ȵǴ°� ���ֱ� ���� ��� ����
	
// ***************** �� ��° ȭ�� ���� **************************
	@FXML private Button tab2BtnSecond;
// ***************** �� ��° ȭ�� ���� **************************	
	@FXML private Button tab3BtnThird;

	public Stage mainStage;


	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		tab1BtnFirst.setOnAction(event-> {handlerTab1BtnFirstAction();});
		tab2BtnSecond.setOnAction(event-> {handlerTab2BtnSecondAction();});
		tab3BtnThird.setOnAction(event-> {handlerTab3BtnThirdAction();}); // 3���� ��ư : �α���ȭ������ ���ư��� �׼�
		
		//1. ���̺�� �⺻����
		setTab1TableView();
		
		//1-2. �޺��ڽ�(�г�, ��) ������ �Ѵ�.
		setComboBoxGradeBan();
		
		//1-3. ���� �ؽ�Ʈ�ʵ� ���˼���
		setTextFieldFormatSetting();
		
		
		//2. ��ư�� �Է��ʵ� �ʱ⼳��
		setButtonTextFieldInitiate("ó��");
		
		//3. ���� ��ư�� ������ �� ó���ϴ� �Լ�
	    t1BtnSum.setOnAction(event -> {handleT1BtnSumAction();}); 
	//  t1BtnSum.setOnAction(event -> handleT1BtnSumAction()); �̷��� �ص� �ȴ�

		//4. ��� ��ư�� ������ �� ó���ϴ� �Լ�
		t1BtnAvg.setOnAction(event -> {handleT1BtnAvgAction();});
		
		//5. �����Է� ��ư�� ������ �� ó���ϴ� �Լ�
		t1BtnReset.setOnAction(event -> {handleT1BtnResetAction(0);});
		
		//6. ��� ��ư�� ������ �� ó���ϴ� �Լ�
		t1BtnReg.setOnAction(event -> {handleT1BtnRegAction();});
		
		//7. ���� ��ư ������ �� ó���ϴ� �Լ�
		t1BtnExit.setOnAction(event -> {Platform.exit();});
		
		//8. ���̺�並 ������ �� ó���ϴ� �Լ� (�ѹ�Ŭ��)
		t1TableView.setOnMouseClicked(event-> {handleT1TableViewAction(event);});
				
		//9. ������ư ������ �� ó���ϴ� �Լ�
		t1BtnEdit.setOnAction(event-> {handleT1BtnEditAction();});
		
		//10. ������ư ������ �� ó���ϴ� �Լ�
		t1BtnDel.setOnAction(event-> {handleT1BtnDelAction();});
		
		//11. �˻���ư ������ �� ó���ϴ� �Լ�
		t1BtnSearch.setOnAction(event-> {handleT1BtnSearchAction();});
		
		//12. ����Ʈ ��ư�� ������ �� ó���ϴ� �Լ� (���ο� ���������� ����Ʈ ������)
		t1BtnBarChart.setOnAction(event-> {handleT1BtnBarChartAction();});
		
		//13. ���� ��ư�� ������ �� ó���ϴ� �Լ� (���ο� ���������� ����Ʈ ������)
		t1BtnEdit.setOnAction(event-> {handleT1BtnEditAction();});
		
	}
	
	//1. ���̺�� �⺻����
	private void setTab1TableView() {

		TableColumn tcNo = t1TableView.getColumns().get(0);                 // 5�ܰ� �߰�
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

	//1-2. �޺��ڽ�(�г�, ��) ������ �Ѵ�.
	private void setComboBoxGradeBan() {
		t1ListGrade.addAll("1�г�","2�г�","3�г�","4�г�","5�г�","6�г�");
		t1ListBan.addAll("1��","2��","3��","4��","5��","6��");
		t1CmbGrade.setItems(t1ListGrade);
		t1CmbBan.setItems(t1ListBan);
	}
	
	//1-3. ���� �ؽ�Ʈ�ʵ� ���˼���
	private void setTextFieldFormatSetting() {
		inputDecimalFormatTwoDgit(t1TextNo); // 2�ڸ�
		inputDecimalFormat(t1TextLanguage);
		inputDecimalFormat(t1TextEnglish);
		inputDecimalFormat(t1TextMath);
		inputDecimalFormat(t1TextScience);
		inputDecimalFormat(t1TextSociety);
		inputDecimalFormat(t1TextMusic);
	}
		
	//2. ��ư�� �Է��ʵ� �ʱ⼳�� 
	private void setButtonTextFieldInitiate(String message) {
		t1DatePicker.setEditable(false);
		t1TextSum.setEditable(false);
		t1TextAvg.setEditable(false);
		switch(message) {
		case "ó��" : 
		case "�����Է�" :			
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
			t1TableView.getSelectionModel().clearSelection(); // ���̺�� ���� ���ֱ�
			
			break;
		case "����" :
			t1BtnSum.setDisable(true);
			t1BtnAvg.setDisable(false);
			t1BtnReg.setDisable(true);
			t1BtnEdit.setDisable(true);
			t1BtnDel.setDisable(true);
			break;
		case "���" :
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
		case "���̺��Ŭ��" :
			t1BtnSum.setDisable(true);
			t1BtnAvg.setDisable(true);			
			t1BtnReg.setDisable(true);			
			t1BtnEdit.setDisable(false);			
			t1BtnDel.setDisable(false);
			
			t1CmbGrade.setDisable(true);
			t1CmbBan.setDisable(true);
			
			t1DatePicker.setDisable(true);
			
			
			
			// �޺��ڽ��� ��������Ŀ�� setEditable �ȵǳ� Disable�� ���� 
			// �����غ��� �������� Disable�� �ؾ߰ڴ�
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
			
			// ����, ��� ��ư�� 
			t1TextSum.setDisable(true);
			t1TextAvg.setDisable(true);
			
			break;
		}		
	}

	//3. ���� ��ư�� ������ �� ó���ϴ� �Լ�	
	private void handleT1BtnSumAction() {
		int language = Integer.parseInt(t1TextLanguage.getText()); // �� �� �������� �̰� ��������
		int english = Integer.parseInt(t1TextEnglish.getText());
		int math = Integer.parseInt(t1TextMath.getText());
		int science = Integer.parseInt(t1TextScience.getText());
		int society = Integer.parseInt(t1TextSociety.getText());
		int music = Integer.parseInt(t1TextMusic.getText());
	
		String message ="�����Է� :";
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
		
		if(!message.equals("�����Է� :")) {
			message +="������ �� �Է����ּ���.";
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
		
		Ȥ��
		
		t1TextSum.setText( ""+( 
		Integer.parseInt(t1TextLanguage.getText())+
		Integer.parseInt(t1TextEnglish.getText())+
		Integer.parseInt(t1TextMath.getText())+
		Integer.parseInt(t1TextScience.getText())+
		Integer.parseInt(t1TextSociety.getText())+
		Integer.parseInt(t1TextMusic.getText())
		));		
	    
	    �̷������ε� �����ϴ�. �ϳ��� ���ڸ�("") ���δ� ���ڷ� �ǰ�
	    ���ʿ� int�� �ƴϴϱ� String.valueOf�� ���ص� �ȴ�
		
	    �ٵ� ���� ���� Ȯ�� �� �����ϰ� �ϱ� ���� ���� ����� ���� �� ����*/

		setButtonTextFieldInitiate("����");
		
	}

	//4. ��� ��ư�� ������ �� ó���ϴ� �Լ�
	private void handleT1BtnAvgAction() {
		t1TextAvg.setText(String.format("%.2f",Double.parseDouble(t1TextSum.getText())/6));
		setButtonTextFieldInitiate("���");		
	}
	
	//5. �����Է� ��ư�� ������ �� ó���ϴ� �Լ�
	private void handleT1BtnResetAction(int a) { // �����Է¶� ����̶� �����ϴ� �Ű�����
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
		
		setButtonTextFieldInitiate("�����Է�"); // "ó��"�� ����
	}

	//6. ��� ��ư�� ������ �� ó���ϴ� �Լ�
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
			// (0,1)�̸� �ձ��� ù�ڸ��� ����� ��
		} else {
			ban = t1CmbBan.getSelectionModel().getSelectedItem().substring(0, 1).trim();
		}
		
		studentNo = t1CmbGrade.getSelectionModel().getSelectedItem().substring(0, 1).trim() + ban + no ;
				
		// Student student = new Student( ~ )
		// t1ListData.add(student)   �̷� ������ �ص� �Ǵµ� ���ٷ� �ѰŴ�
		// �� ���� ������ ������ ����Ѵٸ� ���ٷ� �ϴ°� ����
		
		t1ListData.add(new Student(
							studentNo, // Student no ������ studentNo��(�ټ��ڸ� ��)�� ����
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
	
		handleT1BtnResetAction(1); // �����Է� ��ư �Լ� : 0 ������ ��ü �ʱ�ȭ
		                           // ������ ���ڴ� ��¥+�г�+�� �ʱ�ȭ x
	}
	
	//7. ���� ��ư ������ �� ó���ϴ� �Լ� (���̺귯��)

	//8. ���̺�並 Ŭ������ �� ó���ϴ� �Լ� (��Ŭ��->����, ����Ŭ��->������Ʈ) 
	private void handleT1TableViewAction(MouseEvent event) {
		// ��Ŭ�� - ���� ->
		
		selectStudent = t1TableView.getSelectionModel().getSelectedItem(); 
		// �̰� ���� �ȵǴ°� Student Ÿ���ε�, ��ü�� ���� ���� �ƴѵ� getName()�� ���� �� �ϳ�?
		// New�� ��ü�� ����°� �� Ŭ������ ��� ������ ��ƿ��°Ű�
		// �̰� �� Ŭ������ �͵��� ���ڴٴ� �׷� ���ΰ�..?
		
		// t1TableView�� <Student>�ϱ� �̰͵� Student Ŭ������ �ϴ°ǰ�����
		                     
		selectStudentIndex = t1TableView.getSelectionModel().getSelectedIndex();
		if(event.getClickCount() == 1) {
			
			t1DatePicker.setValue(LocalDate.parse(selectStudent.getDate()));
			// parse ��ɾ� �� �𸣰ڴ�
			
			t1TextNo.setText(selectStudent.getNo().substring(3)); 
			// setText ���� ��Ʈ�����;��ϰ� Student Ŭ�������� getNo()�� 
			// Student Ŭ������ ������� no�� ��Ʈ������ �������°Ű�, �ű⼭ substring���� �� 3�ڸ��� �ڸ��� ��
			// substring���� (2, 4) �̷��� ���� 2��°�ڸ����� �ڸ���, 4��°�ڸ����� ������ 
			// ex) abcdefg -> cd
			// �ٵ� ���⼭ substring(2)�� �ϸ� �� �ƹ��͵� �ȶ���..? 10221�̸� 221�� �Ǿ�� �ϴµ�
			// substring(2,3) �ϸ� 10221�̸� 2�� �� �ߴµ� �� (2)�� �ϸ� �ƹ��͵� �ȶ���
			
			t1TextName.setText(selectStudent.getName());
			t1CmbGrade.getSelectionModel().select(selectStudent.getGrade());
			t1CmbBan.getSelectionModel().select(selectStudent.getBan());		
			
			if(selectStudent.getGender().equals("����")) {
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
			
//			editFlag = true; // �÷��� ��� �߿� 
//			// true�̱� ������ ��չ�ư ������ ���� ��ư Ȱ��ȭ ��
			
			setButtonTextFieldInitiate("���̺��Ŭ��"); 
		// ����Ŭ�� - ������Ʈ 
		}else if (event.getClickCount() == 2) {
		   	try {
				Stage pieStage = new Stage(StageStyle.UTILITY);
				pieStage.initModality(Modality.WINDOW_MODAL);
				pieStage.initOwner(mainStage);
				pieStage.setTitle(selectStudent.getName()+"'s PieChart");
				FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/piechart.fxml"));
				// ������ (C:���� �� ���°�), ����� (�ڱ� �ڽ����κ��� ���°� - ../ ���� ���� �ö󰡴� �� ../../../ ���� �ö󰡴� �� ./ �ڱ��ڽ�)
				Parent root = loader.load();
				
				//**********���̵� ã�ƿ��� = @FXML private Button c1BtnExit**********
				PieChart c2PieChart = (PieChart) root.lookup("#c2PieChart");
				Button c2BtnExit = (Button) root.lookup("#c2BtnExit");

				//*********************�̺�Ʈ ��� �� �ʱ�ȭ, �ڵ鷯 ����*************
				// ������Ʈ �ʱ�ȭ �۾�
				ObservableList pieList = FXCollections.observableArrayList();
				pieList.add(new PieChart.Data("����", Integer.parseInt(selectStudent.getLanguage())));
				pieList.add(new PieChart.Data("����", Integer.parseInt(selectStudent.getEnglish())));
				pieList.add(new PieChart.Data("����", Integer.parseInt(selectStudent.getMath())));
				pieList.add(new PieChart.Data("����", Integer.parseInt(selectStudent.getScience())));
				pieList.add(new PieChart.Data("��ȸ", Integer.parseInt(selectStudent.getSociety())));
				pieList.add(new PieChart.Data("����", Integer.parseInt(selectStudent.getMusic())));
								
				c2PieChart.setData(pieList);
				//******************************************************************** 
				c2BtnExit.setOnAction(e -> {pieStage.close();});
				//********************************************************************
				Scene scene = new Scene(root);
				pieStage.setScene(scene);		
				pieStage.show();				
				
			} catch (IOException e) {
				callAlert("������Ʈ â ���� : ����Ʈ â ������ �߻��߽��ϴ�.");
			}			
						
		}else {
			return;
		}
 
	}
	

	   //9. ������ư ������ �� ó���ϴ� �Լ� (���ο� ��������)
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
			// �ѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤ�
			
			// ***********���̵� ã�ƿ���****************************************
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
	
			
			// ***** �޺��ڽ� ���� (�г�, ��, ����) **********
			editListGrade.addAll("1�г�", "2�г�", "3�г�", "4�г�", "5�г�", "6�г�");
			editListBan.addAll("1��", "2��", "3��", "4��", "5��", "6��");
			editListGender.addAll("����", "����");
			editCmbGrade.setItems(editListGrade);
			editCmbBan.setItems(editListBan);
			editCmbGender.setItems(editListGender);
			
			
			//******* ������ �ٲٸ� �����ư ��Ȱ��ȭ �ǰ�, ����ư ������ Ȱ��ȭ�ǰ� �ϱ� ***********  
			//        ����ĭ�� �����⸸�ϸ� ��Ȱ��ȭ �ǰ� �ϵ��� 
			// => ���� : ��� �ȴ����� ���� ������ ����ǰ� �Ǿ��ִ� ���� �ִ�                              #############################
			
			
//			 if (t1TextLanguage != editTxtLanguage)

			
			
			//******* �����̶�, ����� ���Ƿ� �ٲ� �� ���� ������θ� �ϰ� ���Ƶα� *******************
			
//			editTxtSum.setDisable(true);
//			editTxtAvg.setDisable(true);
			
			editTxtSum.setEditable(false);
			editTxtAvg.setEditable(false);
						
			// �����̸Ӹ� Ű�� �ٲ� �� �����ϱ� �г� �� ��ȣ�� ���ٲٰ� �ϱ� �ؾ��ϴµ�
			// �г� �ö󰡸�..? �׷��� ���б� �й�ó�� �� ���°ɷ� �ؾ� �±��ϳ�..
			// �����̸Ӹ�Ű��... ��ϳ⵵+�����+ ... ��... ����Ʈ�� ȸ����ȣ ��� �ο��ұ�?
			
			// �ϴ�, �����ͺ��̽��� ���踦 ���ؼ� �г�, ��, ��ȣ�� ���ٲٰ� �ص���
			
			editCmbGrade.setDisable(true); // ���� �޺��ڽ� ���� �����߳� �ؽ�Ʈ�� �ص� �Ǵµ�
			editCmbBan.setDisable(true);			
			editTxtNo.setDisable(true);
			
			// DatePicker �����Է� ���ϰ��ϰ� �����ϰ� ��
			editDatePicker.setEditable(false);
			
			//**********************�� �ڵ� ���� **********************************
			
			selectStudent = t1TableView.getSelectionModel().getSelectedItem();
			selectStudentIndex = t1TableView.getSelectionModel().getSelectedIndex(); 
			// ���� �̰� �� �ޱ򸮳� �̰� �� �ϴ°��� �׸��� ����� �� �̰� ���µ� ������ �� �̰� �ְ�
			
			editDatePicker.setValue(LocalDate.parse(selectStudent.getDate()));
			
			editTxtNo.setText(selectStudent.getNo().substring(3));
			editTxtName.setText(selectStudent.getName());

			editCmbGrade.getSelectionModel().select(selectStudent.getGrade());
			editCmbBan.getSelectionModel().select(selectStudent.getBan());
			editCmbGender.getSelectionModel().select(selectStudent.getGender());
			
//			if(selectStudent.getGender().equals("����")) {
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

			//******* ��� ��ư�� ������ �� ������ ��� ���
			editBtnCal.setOnAction(event -> {
				int language = Integer.parseInt(editTxtLanguage.getText()); // �� �� �������� �̰� ��������
				int english = Integer.parseInt(editTxtEnglish.getText());
				int math = Integer.parseInt(editTxtMath.getText());
				int science = Integer.parseInt(editTxtScience.getText());
				int society = Integer.parseInt(editTxtSociety.getText());
				int music = Integer.parseInt(editTxtMusic.getText());
			
				String message ="�����Է� :";
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
				if(!message.equals("�����Է� :")) {
					message +="������ �� �Է����ּ���.";
					callAlert(message);
					return;
				}
				
				
				editTxtSum.setText(String.valueOf(language+english+math+science+society+music));
				editTxtAvg.setText(String.format("%.2f", Double.parseDouble(editTxtSum.getText())/6));
								
			});
			
			//**************** ���� ��ư ������ �� �����ϰ� â ������ ���� �Է�â �ʱ�ȭ ********
			editBtnSave.setOnAction(event -> {
				
				t1ListData.remove(selectStudentIndex);
				// ����� �� selecStudentIndex�� �ִ��� �𸣰ڳ� add �Լ� �����ε��� ã�ƺ���
			
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
					// (0,1)�̸� �ձ��� ù�ڸ��� ����� ��
				} else {
					ban = editCmbBan.getSelectionModel().getSelectedItem().substring(0, 1).trim();
				}

				studentNo = editCmbGrade.getSelectionModel().getSelectedItem().substring(0, 1).trim() + ban + no;

		
				t1ListData.add(selectStudentIndex, new Student(
						studentNo, // Student no ������ studentNo��(�ټ��ڸ� ��)�� ����
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
				callAlert("�����Ϸ� : " + selectStudent.getName() + "���� ������ �Ϸ�Ǿ����ϴ�");
				editStage.close();
				// Platform.exit(); �̰��ϸ�, ��ü�� ������
			}); 
			
			
			
			//**************** ��� ��ư ������ �� â �ݱ� ***********************
			editBtnCancel.setOnAction(event -> {editStage.close();});			
			
 
			
			// �ѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤ�
			Scene scene = new Scene(root); 
			editStage.setScene(scene);     
			editStage.show();

		} catch (IOException e) {
			callAlert("����â ���� : ����â ������ �߻��߽��ϴ�.");
		}

	}
	   
	//10. ������ư ������ �� ó���ϴ� �Լ�
	private void handleT1BtnDelAction() {
		t1ListData.remove(selectStudentIndex);
		editFlag = false; // �����ϰ��� ���̺�� �����־ ��� �ȶߴϱ� �̰� �־���
		
		// �����Է� ��ư�� ������ ���� �Լ� ��
		handleT1BtnResetAction(0);
		callAlert("�����Ϸ� : "+selectStudent.getName() +"���� �����Ǿ����ϴ�");
	}

	//11. �˻���ư ������ �� ó���ϴ� �Լ�
	private void handleT1BtnSearchAction() {
		for (Student student :t1ListData) {
			if(t1TextSearch.getText().trim().equals(student.getName())) {
				t1TableView.getSelectionModel().select(student);
			}
		} // end of for each
	}

	//12. ����Ʈ ��ư�� ������ �� ó���ϴ� �Լ� (���ο� ���������� ����Ʈ ������)
	private void handleT1BtnBarChartAction() {
		try {
			Stage bcStage = new Stage(StageStyle.UTILITY);
			bcStage.initModality(Modality.WINDOW_MODAL);
			bcStage.initOwner(mainStage);			
			bcStage.setTitle("Total Student BarChart");
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/barchart.fxml"));
			Parent root = loader.load();
			//**********���̵� ã�ƿ��� = @FXML private Button c1BtnExit**********
			BarChart c1BarChart = (BarChart) root.lookup("#c1BarChart");
			Button c1BtnExit = (Button) root.lookup("#c1BtnExit");
			//*********************�̺�Ʈ ��� �� �ʱ�ȭ, �ڵ鷯 ����*************
			// ����Ʈ �ʱ�ȭ �۾�
			
			// ����
			XYChart.Series seriesLanguage = new XYChart.Series<>();
			seriesLanguage.setName("����");
			ObservableList languageList = FXCollections.observableArrayList();
			for (int i=0; i<t1ListData.size(); i++) {
				languageList.add(new XYChart.Data<>(t1ListData.get(i).getName(), Integer.parseInt(t1ListData.get(i).getLanguage())));
			}
			seriesLanguage.setData(languageList);
			c1BarChart.getData().add(seriesLanguage);
	
			
			// ����
			XYChart.Series seriesEnglish = new XYChart.Series<>();
			seriesEnglish.setName("����");
			ObservableList englishList = FXCollections.observableArrayList();
			for (int i=0; i<t1ListData.size(); i++) {
				englishList.add(new XYChart.Data<>(t1ListData.get(i).getName(), Integer.parseInt(t1ListData.get(i).getEnglish())));
			}
			seriesEnglish.setData(englishList);
			c1BarChart.getData().add(seriesEnglish);
			
			// ����
			XYChart.Series seriesMath = new XYChart.Series<>();
			seriesMath.setName("����");
			ObservableList mathList = FXCollections.observableArrayList();
			for (int i=0; i<t1ListData.size(); i++) {
				mathList.add(new XYChart.Data<>(t1ListData.get(i).getName(), Integer.parseInt(t1ListData.get(i).getMath())));
			}
			seriesMath.setData(mathList);
			c1BarChart.getData().add(seriesMath);
			
			// ����
			XYChart.Series seriesScience = new XYChart.Series<>();
			seriesScience.setName("����");
			ObservableList scienceList = FXCollections.observableArrayList();
			for (int i=0; i<t1ListData.size(); i++) {
				scienceList.add(new XYChart.Data<>(t1ListData.get(i).getName(), Integer.parseInt(t1ListData.get(i).getScience())));
			}
			seriesScience.setData(scienceList);
			c1BarChart.getData().add(seriesScience);
			
			// ��ȸ
			XYChart.Series seriesSociety = new XYChart.Series<>();
			seriesSociety.setName("����");
			ObservableList societyList = FXCollections.observableArrayList();
			for (int i=0; i<t1ListData.size(); i++) {
				societyList.add(new XYChart.Data<>(t1ListData.get(i).getName(), Integer.parseInt(t1ListData.get(i).getSociety())));
			}
			seriesSociety.setData(societyList);
			c1BarChart.getData().add(seriesSociety);
			
			// ����
			XYChart.Series seriesMusic = new XYChart.Series<>();
			seriesMusic.setName("����");
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
			callAlert("����Ʈ â ���� : ����Ʈ â ������ �߻��߽��ϴ�.");
		}
		
		// ������ (C:���� �� ���°�), ����� (�ڱ� �ڽ����κ��� ���°� - ../ ���� ���� �ö󰡴� �� ../../../ ���� �ö󰡴� �� ./ �ڱ��ڽ�)
	}
	
	public void handlerTab1BtnFirstAction(){
		callAlert("�˸�â : ù��° ȭ���Դϴ�.");
	}
	
	private void handlerTab2BtnSecondAction() {
		callAlert("�˸�â : �ι�° ȭ���Դϴ�.");
	}
	
	// �� 3����ư �ٽ� �α���ȭ������ ���� �׼�
	private void handlerTab3BtnThirdAction() {
		try {
			Stage primaryStage = new Stage();
			FXMLLoader loader=new FXMLLoader(getClass().getResource("../View/login.fxml"));
			Parent root = loader.load();
			
			RootController rootController = loader.getController();
			rootController.primaryStage=primaryStage; // ȭ�� ü���� �߰�
			
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("../application/add.css").toString());
			primaryStage.setScene(scene);
			mainStage.close(); //���� ����ȭ�� �ݱ�
			primaryStage.show();
			
			callAlert("ȭ����ȯ���� : ����ȭ������ ��ȯ�Ǿ����ϴ�.");
		}catch (IOException e) {
			callAlert("ȭ����ȯ���� : ȭ�� ��ȯ�� ������ �ֽ��ϴ�.");
		}
	}
	// ��Ÿ : �˸�â = "�������� : ���� ����� �Է����ּ���" (�� �ݷ��� �����ٰ�)
	public static void callAlert(String contentText) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("�˸�â");
		alert.setHeaderText(contentText.substring(0, contentText.lastIndexOf(":")));
		alert.setContentText(contentText.substring(contentText.lastIndexOf(":")+1));
		alert.showAndWait();
	}

	// ��Ÿ : �ؽ�Ʈ�ʵ� ���˼��� - ���� ���ڸ��� �޴� ����
	private void inputDecimalFormat(TextField textField) {
		
		// ���ڸ� �Է�(������ �Է¹���), �Ǽ��Է¹ް������ new DecimalFormat("###.#");
		DecimalFormat format = new DecimalFormat("###");
		// ���� �Է½� ���� ���� �̺�Ʈ ó��
		textField.setTextFormatter(new TextFormatter<>(event -> {
		
			//�Է¹��� ������ ������ �̺�Ʈ�� ������.  
		if (event.getControlNewText().isEmpty()) { return event; }
		//������ �м��� ���� ��ġ�� ������. 
		    	ParsePosition parsePosition = new ParsePosition(0);
			//�Է¹��� ����� �м���ġ�� �������������� format ����� ��ġ���� �м���.
		    	Object object = format.parse(event.getControlNewText(), parsePosition); 
		//���ϰ��� null �̰ų�, �Է��ѱ��̿� �����м���ġ���� ���������(�� �м������������� ����) �ų�, �Է��ѱ��̰� 4�̸�(3�ڸ��� �Ѿ����� ����.) �̸� null ������. 
		if (object == null || parsePosition.getIndex()<event.getControlNewText().length()
		      || event.getControlNewText().length() == 4) {
		     return null;    
		}else {
		     return event;    
		}   
		}));
				
	}
	
	// ��Ÿ : �ؽ�Ʈ�ʵ� ���˼��� - ���� ���ڸ��� �޴� ����
	private void inputDecimalFormatTwoDgit(TextField textField) {
			
		// ���ڸ� �Է�(������ �Է¹���), �Ǽ��Է¹ް������ new DecimalFormat("###.#");
		DecimalFormat format = new DecimalFormat("##");
		// ���� �Է½� ���� ���� �̺�Ʈ ó��
		textField.setTextFormatter(new TextFormatter<>(event -> {
			
		//�Է¹��� ������ ������ �̺�Ʈ�� ������.  
		if (event.getControlNewText().isEmpty()) { return event; }
			//������ �м��� ���� ��ġ�� ������. 
		    ParsePosition parsePosition = new ParsePosition(0);
			//�Է¹��� ����� �м���ġ�� �������������� format ����� ��ġ���� �м���.
			Object object = format.parse(event.getControlNewText(), parsePosition); 
			//���ϰ��� null �̰ų�, �Է��ѱ��̿� �����м���ġ���� ���������(�� �м������������� ����) �ų�, �Է��ѱ��̰� 4�̸�(3�ڸ��� �Ѿ����� ����.) �̸� null ������. 
			if (object == null || parsePosition.getIndex()<event.getControlNewText().length()
			      || event.getControlNewText().length() == 3) {
			     return null;    
			}else {
			     return event;    
			}   
			}));			
		}
	
}
