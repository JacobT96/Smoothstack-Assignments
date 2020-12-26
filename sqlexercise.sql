/* Query 1 */
select bc.noOfCopies from tbl_book_copies bc, tbl_book b, tbl_library_branch lb
where bc.bookId = b.bookId and bc.branchId = lb.branchId and lb.branchName = "Sharpstown" and b.title = "The Lost Tribe";

/* Query 2 */
select bc.noOfCopies, lb.branchName from tbl_book_copies bc, tbl_book b, tbl_library_branch lb
where bc.bookId = b.bookId and bc.branchId = lb.branchId and b.title = "The Lost Tribe";

/* Query 3 */
select bo.name from tbl_borrower bo left outer join tbl_book_loans bl on bo.cardNo = bl.cardNo
group by bo.cardNo
having count(bl.cardNo) = 0;

/* Query 4 */
select b.title, bo.name, bo.address
from tbl_book b, tbl_book_loans bl, tbl_borrower bo, tbl_library_branch lb
where b.bookId = bl.bookId and bl.cardNo = bo.cardNo and bl.branchId = lb.branchId
and lb.branchName = "Sharpstown" and bl.dueDate = "2020-12-25 00:00:00";

/* Query 5 */
select lb.branchName, count(bl.branchId) from tbl_library_branch lb left outer join tbl_book_loans bl on lb.branchId = bl.branchId
group by bl.branchId;

/* Query 6 */
select bo.name, bo.address, count(*) from tbl_borrower bo, tbl_book_loans bl
where bo.cardNo = bl.cardNo
group by bl.cardNo
having count(*) > 5;

/* Query 7 */
select b.title, bc.noOfCopies from tbl_book b, tbl_author a, tbl_library_branch lb, tbl_book_copies bc
where b.authId = a.authorId and a.authorName = "Stephen King" and b.bookId = bc.bookId and bc.branchId = lb.branchId and lb.branchName = "Central";