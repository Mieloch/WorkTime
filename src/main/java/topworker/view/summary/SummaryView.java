package topworker.view.summary;

import com.vaadin.data.Property;
import com.vaadin.data.validator.DateRangeValidator;
import org.springframework.context.annotation.Scope;
import com.vaadin.data.Item;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;
import topworker.model.service.WorkPeriodService;
import topworker.model.bo.WorkDay;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Echomil on 2016-02-24.
 */

@Scope(value = WebApplicationContext.SCOPE_REQUEST)
@SpringView(name = SummaryView.VIEW_NAME)
public class SummaryView extends HorizontalLayout implements View {
    public static final String VIEW_NAME = "summary";

    private VerticalLayout leftLayout;
    private VerticalLayout contentLayout;
    private DateField beginDateField;
    private DateField endDateField;
    private Button searchButton;
    private Table workDayTable;
    private Label sumLabel;
    private DateRangeValidator dateRangeValidator;
    private final String datePattern = "dd-MM-yyyy";

    @Autowired
    private WorkPeriodService workPeriodService;

    @PostConstruct
    private void init() {
        initLayout();
        createComponents();
        addComponents();
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        System.out.println("Summary View");
    }

    private void initLayout() {
        leftLayout = new VerticalLayout();
        leftLayout.setSizeFull();
        leftLayout.setMargin(true);
        contentLayout = new VerticalLayout();
        contentLayout.setSizeFull();
        contentLayout.setMargin(true);
        setSizeFull();
        addComponent(leftLayout);
        addComponent(contentLayout);
        setExpandRatio(leftLayout, 1.0f);
        setExpandRatio(contentLayout, 6.0f);
    }

    private void createComponents() {
        beginDateField = new DateField("Od");
        beginDateField.setDateFormat(datePattern);
        beginDateField.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                if(dateRangeValidator != null){
                    endDateField.removeValidator(dateRangeValidator);
                }
                dateRangeValidator = new DateRangeValidator("Niedozwolona data",beginDateField.getValue(),new Date(),null);
                endDateField.addValidator(dateRangeValidator);
            }
        });
        endDateField = new DateField("Do");
        endDateField.setDateFormat(datePattern);
        endDateField.setImmediate(true);
        searchButton = new Button("Szukaj");
        searchButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                    fillWorkDayTable();
            }
        });
        workDayTable = new Table();
        workDayTable.setSizeFull();
        workDayTable.addContainerProperty("Data", String.class, "");
        workDayTable.addContainerProperty("Czas", String.class, "");
        sumLabel = new Label("Suma: 0h 0m");
    }

    private void addComponents() {
        leftLayout.addComponent(beginDateField);
        leftLayout.addComponent(endDateField);
        leftLayout.addComponent(searchButton);
        leftLayout.setComponentAlignment(searchButton, Alignment.MIDDLE_CENTER);
        contentLayout.addComponent(workDayTable);
        contentLayout.addComponent(sumLabel);
        contentLayout.setExpandRatio(workDayTable, 6.0f);
        contentLayout.setExpandRatio(sumLabel, 1.0f);
    }


    private void fillWorkDayTable() {
        List<WorkDay> workDayList = workPeriodService.getWorkDays(beginDateField.getValue(), endDateField.getValue());
        int i = 0, sum = 0;
        SimpleDateFormat dateFormat = new SimpleDateFormat(datePattern);
        for (WorkDay workDay : workDayList) {
            Item item = workDayTable.addItem("" + i);
            item.getItemProperty("Data").setValue(dateFormat.format(workDay.getDate()));
            item.getItemProperty("Czas").setValue(formatWorkDuration(workDay.getWorkDurationMinutes()));
            sum += workDay.getWorkDurationMinutes();
            i++;
        }
        sumLabel.setValue("Suma: " + formatWorkDuration(sum));
    }

    private String formatWorkDuration(int minutes) {
        return "" + minutes / 60 + "h " + minutes % 60 + "m";
    }


}
