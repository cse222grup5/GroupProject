package main;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.scene.Scene; 
import javafx.scene.layout.*; 

import javafx.scene.Group; 
import javafx.scene.control.*;

import java.util.ArrayList;
//import java.beans.EventHandler;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.JTextField;

import factory.*;


public class MainClass extends Application {
	static TableView<INamedItem>[] tvNamedItems;
	static TableView<INamedItem>[] tvMainItems;
	public static int selectedProductId;
	static TableView<StockRawMaterial> tvRM;
	static TableView<ProcessWorkShopPair> tvPR;
	
	protected static int selectedLibrary;

	public static void main(String[] args) {
		Factory fac=Factory.getInstance();
		//fac.show();
		for(int i=0;i<30;i++){
			fac.addItem(Factory._RAWMATERIAL,new NamedItem(1000+i,"rm"+i));
		}

		fac.addItem(Factory._PROCESS,new NamedItem(1001,"pr1"));
		fac.addItem(Factory._PROCESS,new NamedItem(1002,"pr2"));

		fac.addItem(Factory._WORKSHOP,new NamedItem(1001,"ws1"));
		fac.addItem(Factory._WORKSHOP,new NamedItem(1002,"ws2"));

		fac.addItem(Factory._CUSTOMER,new NamedItem(1001,"cus1"));
		fac.addItem(Factory._CUSTOMER,new NamedItem(1002,"cus2"));
		ArrayList<StockRawMaterial> rm=new ArrayList<StockRawMaterial>();
		rm.add(new StockRawMaterial(1001,1));
		rm.add(new StockRawMaterial(1002,2));
		rm.add(new StockRawMaterial(1001,3));
		ArrayList<ProcessWorkShopPair> pr=new ArrayList<ProcessWorkShopPair>();
		pr.add(new ProcessWorkShopPair(1001,1001));
		pr.add(new ProcessWorkShopPair(1001,1002));
		Product p=new Product(101,"product1",rm,pr);
		fac.addProduct(p);
		
		WorkOrder wo=new WorkOrder(105,"wo1",101,3);
		fac.addWorkOrder(wo);
		fac.show();
		launch(args);
	}
	
	private static<E> TableView<E> getTableView(String[][] source){
		TableView<E> tvRM=new TableView<E>();
		for(String[] si:source){
			TableColumn<E,Integer> col=new TableColumn<>(si[0]);
			col.setMinWidth(120);
			col.setCellValueFactory(new PropertyValueFactory<>(si[1]));
			tvRM.getColumns().add(col);
		}
		return tvRM;
	}
	public static void showProductDetails(int productId){
		Stage stage = new Stage();
		stage.setTitle("Product " + productId+ " "+ Factory.getInstance().getProduct(productId).getName());
		
		stage.initModality(Modality.APPLICATION_MODAL);
		VBox box = new VBox();
		box.setPadding(new Insets(10));
		box.setAlignment(Pos.CENTER);
		Label lblId = new Label(productId+"");
		Label lblName = new Label(Factory.getInstance().getProduct(productId).getName());
		
		String[][] sourceRM={{"ID","id"},{"Name","name"},{"Amount","amount"}};		
		tvRM=getTableView(sourceRM);
		tvRM.setItems(getProductRawMaterialData(productId));
		
		
		String[][] sourcePR={{"Process ID","ProcessId"},{"Process Name","ProcessName"},{"WS Id","WorkShopId"},{"WS Name","WorkShopName"}};
		tvPR=getTableView(sourcePR);
		
		tvPR.setItems(getProductProcessData(productId));
		
		
		Button btnAddRM=new Button("Add Raw Material");
		btnAddRM.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				selectedProductId=productId;
				addRawMaterial();
			}
		});
		
		Button btnAddPR=new Button("Add Process");
		btnAddPR.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				selectedProductId=productId;
				addProcess();
			}
		});
		
		box.getChildren().add(lblId);
		box.getChildren().add(lblName);
		box.getChildren().add(tvRM);
		box.getChildren().add(btnAddRM);
		box.getChildren().add(tvPR);
		box.getChildren().add(btnAddPR);
		
		Scene scene = new Scene(box, 700, 300);
		stage.setScene(scene);
		stage.show();
	}
	public static void addProcess(){
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		
		VBox box = new VBox();
		box.setPadding(new Insets(10));

		box.setAlignment(Pos.CENTER);

		Label label = new Label("Enter process and work shop");
		 ComboBox<INamedItem> combo_box_process = 
                 new ComboBox<>(getLibData(1)); 
		 ComboBox<INamedItem> combo_box_ws = 
                 new ComboBox<>(getLibData(2)); 
		

		Button btnAdd = new Button("Add");
		btnAdd.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				int id_pr=combo_box_process.getValue().getId();
				int id_ws=combo_box_process.getValue().getId();
				Product p=Factory.getInstance().getProduct(selectedProductId);				
				p.addProcess(new ProcessWorkShopPair(id_pr,id_ws));
				stage.close();
				refreshProductDetails();
			}
		});

		Button btnCancel = new Button("Cancel");
		btnCancel.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				stage.close();
			}
		});

		box.getChildren().add(label);
		box.getChildren().add(combo_box_process);
		box.getChildren().add(combo_box_ws);
		box.getChildren().add(btnAdd);
		box.getChildren().add(btnCancel);
		Scene scene = new Scene(box, 250, 150);
		stage.setScene(scene);
		stage.show();
	}
	public static void addRawMaterial(){
		Stage stage = new Stage();
		stage.setTitle("Add Raw Material to Product " + selectedProductId+ " "+ Factory.getInstance().getProduct(selectedProductId).getName());
		stage.initModality(Modality.APPLICATION_MODAL);
		
		VBox box = new VBox();
		box.setPadding(new Insets(10));

		box.setAlignment(Pos.CENTER);

		Label label = new Label("Enter raw material and amount");
		 ComboBox<INamedItem> combo_box = 
                 new ComboBox<>(getLibData(0)); 
		TextField textAmount = new TextField();
		textAmount.setPromptText("Enter amount");
		

		Button btnAdd = new Button("Add");
		btnAdd.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				int id=combo_box.getValue().getId();
				
				Product p=Factory.getInstance().getProduct(selectedProductId);
				int amnt=Integer.parseInt(textAmount.getText());
				p.addRawMaterial(new StockRawMaterial(id,amnt));
				stage.close();
				refreshProductDetails();
			}
		});

		Button btnCancel = new Button("Cancel");
		btnCancel.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				stage.close();
			}
		});

		box.getChildren().add(label);
		box.getChildren().add(combo_box);
		box.getChildren().add(textAmount);
		box.getChildren().add(btnAdd);
		box.getChildren().add(btnCancel);
		Scene scene = new Scene(box, 250, 150);
		stage.setScene(scene);
		stage.show();
	}
	public static void _showAddProduct(){
		Stage stage = new Stage();
		stage.setTitle("Add new Product");
		stage.initModality(Modality.APPLICATION_MODAL);
		
		VBox box = new VBox();
		box.setPadding(new Insets(10));

		box.setAlignment(Pos.CENTER);

		Label label = new Label("Enter id and name");

		TextField textId = new TextField();
		textId.setPromptText("Enter id");
		TextField textName = new TextField();
		textName.setPromptText("Enter name");

		Button btnAdd = new Button("Add");
		btnAdd.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				int id=Integer.parseInt(textId.getText());
				Product p=new Product(id,textName.getText(),new ArrayList<StockRawMaterial>(),new ArrayList<ProcessWorkShopPair>());
				Factory.getInstance().getProductList().put(id, p);
				stage.close();
				refreshProductTable();
			}
		});

		Button btnCancel = new Button("Cancel");
		btnCancel.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				stage.close();
			}
		});

		box.getChildren().add(label);
		box.getChildren().add(textId);
		box.getChildren().add(textName);
		box.getChildren().add(btnAdd);
		box.getChildren().add(btnCancel);
		Scene scene = new Scene(box, 250, 150);
		stage.setScene(scene);
		stage.show();
	}
	public static void showAdd(){
		Stage stage = new Stage();
		stage.setTitle("Add new "+ Factory.headerLib[selectedLibrary]);
		stage.initModality(Modality.APPLICATION_MODAL);
		VBox box = new VBox();
		box.setPadding(new Insets(10));

		box.setAlignment(Pos.CENTER);

		Label label = new Label("Enter id and name");

		TextField textId = new TextField();
		textId.setPromptText("enter id");
		TextField textName = new TextField();
		textName.setPromptText("enter name");

		Button btnAdd = new Button("Add");
		btnAdd.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				int id=Integer.parseInt(textId.getText());
				Factory.getInstance().addItem(MainClass.selectedLibrary,new NamedItem(id,textName.getText()));
				Factory.getInstance().show();				
				stage.close();
				refreshTable(MainClass.selectedLibrary);
			}
		});

		Button btnCancel = new Button();
		btnCancel.setText("Cancel");
		btnCancel.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				stage.close();
			}
		});

		box.getChildren().add(label);
		box.getChildren().add(textId);
		box.getChildren().add(textName);
		box.getChildren().add(btnAdd);
		box.getChildren().add(btnCancel);
		Scene scene = new Scene(box, 250, 150);
		stage.setScene(scene);
		stage.show();
	}
	public static void refreshProductDetails(){
		tvRM.setItems(getProductRawMaterialData(selectedProductId));
		tvPR.setItems(getProductProcessData(selectedProductId));		
	}
	public static void refreshTable(int type){
		tvNamedItems[type].setItems(getLibData(type));
	}
	public static void refreshProductTable(){
		tvMainItems[0].setItems(getMainData(0));
	}
	@Override
	public void start(Stage stage) throws Exception {
		//mainStage=stage;
		stage.setTitle("Factory"); 
		tvNamedItems=new TableView[Factory.headerLib.length];
		tvMainItems=new TableView[Factory.headerMain.length];
		TabPane tabLib = new TabPane(); 
		tabLib.getSelectionModel().selectedItemProperty().addListener(
				new ChangeListener<Tab>() {
					@Override
					public void changed(ObservableValue<? extends Tab> ov, Tab t, Tab t1) {
						MainClass.selectedLibrary=tabLib.getSelectionModel().getSelectedIndex();
						System.out.println("Tab Selection changed" +MainClass.selectedLibrary);

					}
				}
				);

		for (int i = 0; i < Factory.headerLib.length; i++) { 
			Tab tab = new Tab(Factory.headerLib[i]); 
			tab.setClosable(false);
			String[][] sourceNamedItem={{"ID","id"},{"Name","name"}};		
			tvNamedItems[i]=getTableView(sourceNamedItem);
			tvNamedItems[i].setItems(getLibData(i));
			
			//-------------------------------------------------------
			TableColumn<INamedItem, Void> colBtn = new TableColumn<>("Delete");

			Callback<TableColumn<INamedItem, Void>, TableCell<INamedItem, Void>> cellFactory = new Callback<TableColumn<INamedItem, Void>, TableCell<INamedItem, Void>>() {
				@Override
				public TableCell<INamedItem, Void> call(final TableColumn<INamedItem, Void> param) {
					final TableCell<INamedItem, Void> cell = new TableCell<INamedItem, Void>() {

						private final Button btn = new Button("Delete");

						{
							btn.setOnAction((ActionEvent event) -> {
								INamedItem data = getTableView().getItems().get(getIndex());
								Factory.getInstance().getLib(MainClass.selectedLibrary).remove(data.getId());
								tvNamedItems[MainClass.selectedLibrary].refresh();
								//tv.refresh();
								MainClass.refreshTable(MainClass.selectedLibrary);
								System.out.println("selectedData: " + data);
							});
						}

						@Override
						public void updateItem(Void item, boolean empty) {
							super.updateItem(item, empty);
							if (empty) {
								setGraphic(null);
							} else {
								setGraphic(btn);
							}
						}
					};
					return cell;
				}
			};

			colBtn.setCellFactory(cellFactory);
			tvNamedItems[i].getColumns().add(colBtn);

			VBox r = new VBox(); 
			Button b = new Button("Add");
			b.setOnAction(new EventHandler<ActionEvent>(){

				@Override
				public void handle(ActionEvent arg0) {
					showAdd();
				}
			});

			r.getChildren().add(tvNamedItems[i]);
			r.getChildren().add(b); 
			tab.setContent(r);

			tabLib.getTabs().add(tab); 
		} 		

		TabPane tabMain = new TabPane(); 
		for (int i = 0; i < Factory.headerMain.length; i++) { 

			Tab tab = new Tab(Factory.headerMain[i]); 
			tab.setClosable(false);
			if(i==0){
				
				
				String[][] sourceNamedItem={{"ID","id"},{"Name","name"}};		
				tvMainItems[i]=getTableView(sourceNamedItem);
				tvMainItems[i].setItems(getMainData(i));

				//-------------------------------------------------------
				TableColumn<INamedItem, Void> colBtnDelete = new TableColumn<>("Delete");
				Callback<TableColumn<INamedItem, Void>, TableCell<INamedItem, Void>> _cellFactory = new Callback<TableColumn<INamedItem, Void>, TableCell<INamedItem, Void>>() {
					@Override
					public TableCell<INamedItem, Void> call(final TableColumn<INamedItem, Void> param) {
						final TableCell<INamedItem, Void> cell = new TableCell<INamedItem, Void>() {

							private final Button btn = new Button("Delete");

							{
								btn.setOnAction((ActionEvent event) -> {
									INamedItem data = getTableView().getItems().get(getIndex());
									Factory.getInstance().getProductList().remove(data.getId());
									Factory.getInstance().show();
									MainClass.refreshProductTable();
									//System.out.print(data+" deleting");
								});
							}

							@Override
							public void updateItem(Void item, boolean empty) {
								super.updateItem(item, empty);
								if (empty) {
									setGraphic(null);
								} else {
									setGraphic(btn);
								}
							}
						};
						return cell;
					}
				};
				colBtnDelete.setCellFactory(_cellFactory);
				tvMainItems[i].getColumns().add(colBtnDelete);
				//-------------------------------------------------------	
				//-------------------------------------------------------
				
				
				TableColumn<INamedItem, Void> colBtn = new TableColumn<>("Edit");
				Callback<TableColumn<INamedItem, Void>, TableCell<INamedItem, Void>> cellFactory = new Callback<TableColumn<INamedItem, Void>, TableCell<INamedItem, Void>>() {
					@Override
					public TableCell<INamedItem, Void> call(final TableColumn<INamedItem, Void> param) {
						final TableCell<INamedItem, Void> cell = new TableCell<INamedItem, Void>() {

							private final Button btn = new Button("Edit");

							{
								btn.setOnAction((ActionEvent event) -> {
									INamedItem data = getTableView().getItems().get(getIndex());
									showProductDetails(data.getId());
								});								
							}
							@Override
							public void updateItem(Void item, boolean empty) {
								super.updateItem(item, empty);
								if (empty) {
									setGraphic(null);
								} else {
									setGraphic(btn);
								}
							}
						};
						return cell;
					}
				};
				colBtn.setCellFactory(cellFactory);
				tvMainItems[i].getColumns().add(colBtn);
				//-------------------------------------------------------	
				Button b = new Button("Add");
				b.setOnAction(new EventHandler<ActionEvent>(){
					@Override
					public void handle(ActionEvent arg0) {
						_showAddProduct();
					}
				});
				VBox r = new VBox(); 
				r.getChildren().add(tvMainItems[i]);
				r.getChildren().add(b);
				tab.setContent(r);
			}else if(i==1){
				//----------------------------------------------------------WORK ORDER-----------------------
				//
				
				String[][] sourceNamedItem={{"ID","id"},{"Name","name"},{"Product Id","ProductId"},{"Product Name","ProductName"},{"Amount","amount"}};		
				
				tvMainItems[i]=getTableView(sourceNamedItem);
				tvMainItems[i].setItems(getMainData(i));
				VBox r = new VBox(); 
				r.getChildren().add(tvMainItems[i]);
				tab.setContent(r);
				
			}
			tabMain.getTabs().add(tab);
		}
		HBox h=new HBox();
		h.getChildren().add(tabMain);
		h.getChildren().add(tabLib);
		Scene scene = new Scene(h, 1000, 700); 
		stage.setScene(scene); 
		stage.show(); 
	}
	private static ObservableList<INamedItem> getMainData(int type){
		ObservableList<INamedItem> list=FXCollections.observableArrayList();		
		if(type==0){
			HashMap<Integer, Product> hm= Factory.getInstance().getProductList();
			for(Entry<Integer, Product> e:hm.entrySet()){
				list.add(e.getValue());
			}
		}else if(type==1){			
			
			HashMap<Integer, WorkOrder> hm= Factory.getInstance().getWorkOrderList();
			
			for(Entry<Integer, WorkOrder> e:hm.entrySet()){
				System.out.println("----inside--------------"+e.getValue());
				list.add(e.getValue());
				System.out.println(e+"------------------");
			}
		}
		
		return list;
	}
	private static ObservableList<INamedItem> getLibData(int type){
		ObservableList<INamedItem> list=FXCollections.observableArrayList();
		HashMap<Integer, INamedItem> hm=Factory.getInstance().getLib(type);
		for(Entry<Integer, INamedItem> e:hm.entrySet()){
			System.out.println(e.getValue());
			list.add(e.getValue());
		}
		return list;
	}
	private static ObservableList<StockRawMaterial> getProductRawMaterialData(int id){
		ObservableList<StockRawMaterial> list=FXCollections.observableArrayList();
		for(int i=0;i<Factory.getInstance().getProduct(id).getRawMaterialCount();i++){
			list.add(Factory.getInstance().getProduct(id).getRawMaterial(i));
		}
		return list;		
	}
	private static ObservableList<ProcessWorkShopPair> getProductProcessData(int id){
		ObservableList<ProcessWorkShopPair> list=FXCollections.observableArrayList();
		for(int i=0;i<Factory.getInstance().getProduct(id).getProcessCount();i++){
			list.add(Factory.getInstance().getProduct(id).getProcessStep(i));
		}
		return list;
	}
}



