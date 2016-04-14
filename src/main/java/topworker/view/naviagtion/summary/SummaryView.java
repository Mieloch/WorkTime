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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.WebApplicationContext;
import topworker.dal.entity.WorkDay;
import topworker.service.WorkPeriodService;
import topworker.utils.MessagesBundle;
import topworker.utils.TimeUtils;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
    private Locale currentLocale;

    private final String datePattern = "dd-MM-yyyy";

    @Autowired
    private WorkPeriodService workPeriodService;

    @Autowired
    private MessagesBundle messagesBundle;

    @PostConstruct
    private void init() {
        initLayout();
        createComponents();
        addComponents();
        currentLocale = messagesBundle.getLocale();

    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        if (!currentLocale.equals(messagesBundle.getLocale())) {
            removeAllComponents();
            init();
        }
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
        beginDateField = new DateField(messagesBundle.getMessage("from"));
        beginDateField.setDateFormat(datePattern);
        beginDateField.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                if (dateRangeValidator != null) {
                    endDateField.removeValidator(dateRangeValidator);
                }
                dateRangeValidator = new DateRangeValidator(messagesBundle.getMessage("not_allowed_date"), beginDateField.getValue(), new Date(), null);
                endDateField.addValidator(dateRangeValidator);
            }
        });
        endDateField = new DateField(messagesBundle.getMessage("to"));
        endDateField.setDateFormat(datePattern);
        endDateField.setImmediate(true);
        searchButton = new Button(messagesBundle.getMessage("search"));
        searchButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                fillWorkDayTable();
            }
        });
        workDayTable = new Table();
        workDayTable.setSizeFull();

        workDayTable.addContainerProperty("date", String.class, "", messagesBundle.getMessage("date"), null, null);
        workDayTable.addContainerProperty("work_time", String.class, "", messagesBundle.getMessage("work_time"), null, null);
        sumLabel = new Label(messagesBundle.getMessage("summary") + ": 0h 0m");

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
        if (beginDateField.isValid() && endDateField.isValid() && !beginDateField.isEmpty() && !endDateField.isEmpty()) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            List<WorkDay> workDayList = workPeriodService.getWorkDays(beginDateField.getValue(), endDateField.getValue(), auth.getName());
            int i = 0, sum = 0;
            SimpleDateFormat dateFormat = new SimpleDateFormat(datePattern);
            for (WorkDay workDay : workDayList) {
                Item item = workDayTable.addItem("" + i);
                item.getItemProperty("date").setValue(dateFormat.format(workDay.getDate()));
                item.getItemProperty("work_time").setValue(TimeUtils.formatTime(workDay.getWorkDurationMinutes()));
                sum += workDay.getWorkDurationMinutes();
                i++;
            }
            sumLabel.setValue(messagesBundle.getMessage("summary") + ": " + TimeUtils.formatTime(sum));

        }
    }
}
