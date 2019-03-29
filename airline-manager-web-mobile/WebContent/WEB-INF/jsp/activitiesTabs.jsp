<%@ include file="/common/taglibs.jsp"%>

			<security:authorize access="hasAnyRole('ROLE_MEMBER')">
				<s:url var="eventMemberListUrl" action="eventMemberList" namespace="/ajax" />
				<s:url var="eventOfferListUrl" action="eventOfferList" namespace="/ajax" />
				<s:url var="eventItemListUrl" action="eventItemList" namespace="/ajax" />
				<sjm:div id="activitiesNavBar" role="navbar" data-iconpos="left">
					<ul>
						<li><sjm:a href="%{eventMemberListUrl}" id="eventMemberListTab">Member Events</sjm:a></li>
						
						<li><sjm:a href="%{eventOfferListUrl}" id="eventOfferListTab">Offer Events</sjm:a></li>
												
						<li><sjm:a href="%{eventItemListUrl}" id="eventItemListTab">Item Events</sjm:a></li>					
					</ul>
				</sjm:div>
			</security:authorize>
