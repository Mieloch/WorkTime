package topworker.view.naviagtion.summary;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.validator.DateRangeValidator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.WebApplicationContext;
import topworker.model.bo.WorkDay;
import topworker.service.WorkPeriodService;
import topworker.utils.MessagesBundle;
import topworker.utils.TimeUtils;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
        beginDateField = new DateField(MessagesBundle.getMessage("from"));
        beginDateField.setDateFormat(datePattern);
        beginDateField.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                if(dateRangeValidator != null){
                    endDateField.removeValidator(dateRangeValidator);
                }
                dateRangeValidator = new DateRangeValidator(MessagesBundle.getMessage("not_allowed_date"), beginDateField.getValue(), new Date(), null);
                endDateField.addValidator(dateRangeValidator);
            }
        });
        endDateField = new DateField(MessagesBundle.getMessage("to"));
        endDateField.setDateFormat(datePattern);
        endDateField.setImmediate(true);
        searchButton = new Button(MessagesBundle.getMessage("search"));
        searchButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                    fillWorkDayTable();
            }
        });
        workDayTable = new Table();
        workDayTable.setSizeFull();
        workDayTable.addContainerProperty(MessagesBundle.getMessage("date"), String.class, "");
        workDayTable.addContainerProperty(MessagesBundle.getMessage("work_time"), String.class, "");
        sumLabel = new Label(MessagesBundle.getMessage("sum") + ": 0h 0m");
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
            item.getItemProperty(MessagesBundle.getMessage("date")).setValue(dateFormat.format(workDay.getDate()));
            item.getItemProperty(MessagesBundle.getMessage("work_time")).setValue(TimeUtils.formatTime(workDay.getWorkDurationMinutes()));
            sum += workDay.getWorkDurationMinutes();
            i++;
        }
        sumLabel.setValue(MessagesBundle.getMessage("sum") + ": " + TimeUtils.formatTime(sum));
    }



}
