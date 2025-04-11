
    function deleteContact(id) {
        $.ajax({
            url: '/delete/' + id,
            type: 'DELETE',
            success: function(response) {
                alert('Contact deleted successfully.');
                location.reload(); // Reload or update the contact list dynamically
            },
            error: function(error) {
                alert('Error deleting contact: ' + error.responseText);
            }
        });
    }

