<%-- Created by IntelliJ IDEA. User: cbc Date: 2024-10-23 Time: 2:46 p.m. To
change this template use File | Settings | File Templates. --%> <%@ page
contentType="text/html;charset=UTF-8" language="java" %>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<div id="profile">
  <h2>Professional Profile</h2>
  <form id="profileForm">
    <div class="form-group">
      <label for="name">Name</label>
      <input type="text" class="form-control" id="name" name="name" readonly />
    </div>
    <div class="form-group">
      <label for="email">Email</label>
      <input
        type="email"
        class="form-control"
        id="email"
        name="email"
        readonly
      />
    </div>
    <div class="form-group">
      <label for="type">Account Type</label>
      <input type="text" class="form-control" id="type" name="type" readonly />
    </div>
    <div class="form-group">
      <label for="creationDate">Account Creation Date</label>
      <input
        type="text"
        class="form-control"
        id="creationDate"
        name="creationDate"
        readonly
      />
    </div>
    <div class="form-group">
      <label for="currentPosition">Current Position</label>
      <input
        type="text"
        class="form-control"
        id="currentPosition"
        name="currentPosition"
        required
      />
    </div>
    <div class="form-group">
      <label for="currentInstitution">Current Institution</label>
      <input
        type="text"
        class="form-control"
        id="currentInstitution"
        name="currentInstitution"
        required
      />
    </div>
    <div class="form-group">
      <label for="educationBackground">Education Background</label>
      <textarea
        class="form-control"
        id="educationBackground"
        name="educationBackground"
        rows="3"
        required
      ></textarea>
    </div>
    <div class="form-group">
      <label for="areaOfExpertise">Area of Expertise</label>
      <textarea
        class="form-control"
        id="areaOfExpertise"
        name="areaOfExpertise"
        rows="3"
        required
      ></textarea>
    </div>
    <div class="text-right">
      <button type="submit" class="btn btn-primary">Save Profile</button>
    </div>
  </form>
</div>

<div id="searchCourse" style="display: none">
  <h2>Search Course</h2>
  <form id="searchCourseForm">
    <div class="form-group">
      <label for="institution">Institution</label>
      <select class="form-control" id="institution" name="institution" required>
        <option value="">Select Institution</option>
      </select>
    </div>
    <div class="form-group">
      <label for="courseCode">Course Code</label>
      <select class="form-control" id="courseCode" name="courseCode" required>
        <option value="">Select Course Code</option>
      </select>
    </div>
    <div class="form-group">
      <label for="courseTitle">Course Title</label>
      <input
        type="text"
        class="form-control"
        id="courseTitle"
        name="courseTitle"
        readonly
      />
    </div>
    <div class="form-group">
      <label for="term">Term</label>
      <select class="form-control" id="term" name="term" required>
        <option value="">Select Term</option>
      </select>
    </div>
    <div class="form-group">
      <label for="schedule">Schedule</label>
      <select class="form-control" id="schedule" name="schedule" required>
        <option value="">Select Schedule</option>
        <option value="Morning">Morning</option>
        <option value="Afternoon">Afternoon</option>
        <option value="Evening">Evening</option>
      </select>
    </div>
    <div class="form-group">
      <label for="deliveryMethod">Delivery Method</label>
      <select
        class="form-control"
        id="deliveryMethod"
        name="deliveryMethod"
        required
      >
        <option value="">Select Delivery Method</option>
        <option value="In-Person">In-Person</option>
        <option value="Remote">Remote</option>
        <option value="Hybrid">Hybrid</option>
      </select>
    </div>
    <button type="submit" class="btn btn-primary">Search</button>
  </form>
  <div id="searchResults" class="mt-4" style="display: none">
    <h3>Search Results</h3>
    <table id="searchResultsTable" class="table">
      <thead>
        <tr>
          <th><input type="checkbox" id="selectAll" /></th>
          <th>Course Code</th>
          <th>Course Title</th>
          <th>Institution</th>
          <th>Term</th>
          <th>Schedule</th>
          <th>Delivery Method</th>
          <th>Action</th>
        </tr>
      </thead>
      <tbody id="searchResultsList"></tbody>
    </table>
    <button id="requestToTeach" class="btn btn-success">
      Request to Teach Selected Courses
    </button>
  </div>
</div>

<div id="notifications" style="display: none">
    <h2>Notifications</h2>
    <table class="table">
        <thead>
            <tr>
                <th>Message</th>
                <th>Created At</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody id="notificationList">
            <!-- Notifications will be populated dynamically -->
        </tbody>
    </table>
</div>

<script src="${pageContext.request.contextPath}/js/courseSearch.js"></script>

<script>
$(document).ready(function() {
    // Handle Request to Teach button click
    $('#requestToTeach').click(function() {
        var selectedCourses = [];
        $('#searchResultsTable tbody input[type="checkbox"]:checked').each(function() {
            selectedCourses.push($(this).val());
        });
        
        // Send teaching requests
        selectedCourses.forEach(function(courseId) {
            $.ajax({
                url: '${pageContext.request.contextPath}/api/teaching-request',
                method: 'POST',
                data: {
                    courseId: courseId
                },
                success: function(response) {
                    alert('Teaching request submitted successfully');
                    loadNotifications();
                    // Clear checkboxes
                    $('#searchResultsTable tbody input[type="checkbox"]').prop('checked', false);
                    $('#selectAll').prop('checked', false);
                },
                error: function(xhr) {
                    alert('Error submitting teaching request: ' + xhr.responseText);
                }
            });
        });
    });

    // Handle select all checkbox
    $('#selectAll').change(function() {
        $('#searchResultsTable tbody input[type="checkbox"]').prop('checked', $(this).prop('checked'));
    });

    // Handle delete notification click
    $('#notificationList').on('click', '.delete-notification', function() {
        var notificationId = $(this).data('id');
        markNotificationAsRead(notificationId);
    });

    // Initial load and periodic refresh of notifications
    loadNotifications();
    setInterval(loadNotifications, 30000); // Refresh every 30 seconds
});

function loadNotifications() {
    $.ajax({
        url: '${pageContext.request.contextPath}/api/notifications',
        method: 'GET',
        success: function(notifications) {
            var tbody = $('#notificationList');
            tbody.empty();
            
            if (!notifications || notifications.length === 0) {
                tbody.append('<tr><td colspan="3" class="text-center">No unread notifications</td></tr>');
                return;
            }
            
            notifications.forEach(function(notification) {
                var row = $('<tr>');
                row.append($('<td>').text(notification.message));
                row.append($('<td>').text(new Date(notification.createdAt).toLocaleString()));
                
                var actionsCell = $('<td>');
                if (!notification.isRead) {
                    var deleteButton = $('<button>')
                        .addClass('btn btn-danger btn-sm delete-notification')
                        .attr('data-id', notification.notificationId)
                        .html('<i class="fas fa-trash"></i> Delete');
                    actionsCell.append(deleteButton);
                }
                
                row.append(actionsCell);
                tbody.append(row);
            });
        },
        error: function(xhr) {
            $('#notificationList').html(
                '<tr><td colspan="3" class="text-center text-danger">' +
                'Error loading notifications. Please try again later.</td></tr>'
            );
        }
    });
}

function markNotificationAsRead(notificationId) {
    $.ajax({
        url: '${pageContext.request.contextPath}/api/notifications/' + notificationId,
        method: 'PUT',
        contentType: 'application/json',
        data: JSON.stringify({ isRead: true }),
        success: function() {
            loadNotifications(); // Reload the notifications list
        },
        error: function(xhr) {
            alert('Error marking notification as read: ' + xhr.responseText);
        }
    });
}
</script>
