package com.project.travelExperts.data.dto.response;

import java.util.List;

public class PayPalCaptureOrderResponse {
    private String id;
    private String status;
    private PaymentSource paymentSource;
    private List<PurchaseUnit> purchaseUnits;
    private Payer payer;
    private List<Link> links;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public PaymentSource getPaymentSource() {
        return paymentSource;
    }

    public void setPaymentSource(PaymentSource paymentSource) {
        this.paymentSource = paymentSource;
    }

    public List<PurchaseUnit> getPurchaseUnits() {
        return purchaseUnits;
    }

    public void setPurchaseUnits(List<PurchaseUnit> purchaseUnits) {
        this.purchaseUnits = purchaseUnits;
    }

    public Payer getPayer() {
        return payer;
    }

    public void setPayer(Payer payer) {
        this.payer = payer;
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public static class PaymentSource {
        private PayPal paypal;

        public PayPal getPaypal() {
            return paypal;
        }

        public void setPaypal(PayPal paypal) {
            this.paypal = paypal;
        }

        public static class PayPal {
            private String emailAddress;
            private String accountId;
            private String accountStatus;
            private Name name;
            private Address address;

            public String getEmailAddress() {
                return emailAddress;
            }

            public void setEmailAddress(String emailAddress) {
                this.emailAddress = emailAddress;
            }

            public String getAccountId() {
                return accountId;
            }

            public void setAccountId(String accountId) {
                this.accountId = accountId;
            }

            public String getAccountStatus() {
                return accountStatus;
            }

            public void setAccountStatus(String accountStatus) {
                this.accountStatus = accountStatus;
            }

            public Name getName() {
                return name;
            }

            public void setName(Name name) {
                this.name = name;
            }

            public Address getAddress() {
                return address;
            }

            public void setAddress(Address address) {
                this.address = address;
            }

            public static class Name {
                private String givenName;
                private String surname;

                public String getGivenName() {
                    return givenName;
                }

                public void setGivenName(String givenName) {
                    this.givenName = givenName;
                }

                public String getSurname() {
                    return surname;
                }

                public void setSurname(String surname) {
                    this.surname = surname;
                }
            }

            public static class Address {
                private String countryCode;

                public String getCountryCode() {
                    return countryCode;
                }

                public void setCountryCode(String countryCode) {
                    this.countryCode = countryCode;
                }
            }
        }
    }

    public static class PurchaseUnit {
        private String referenceId;
        private Shipping shipping;
        private Payments payments;

        public String getReferenceId() {
            return referenceId;
        }

        public void setReferenceId(String referenceId) {
            this.referenceId = referenceId;
        }

        public Shipping getShipping() {
            return shipping;
        }

        public void setShipping(Shipping shipping) {
            this.shipping = shipping;
        }

        public Payments getPayments() {
            return payments;
        }

        public void setPayments(Payments payments) {
            this.payments = payments;
        }

        public static class Shipping {
            private Name name;
            private Address address;

            public Name getName() {
                return name;
            }

            public void setName(Name name) {
                this.name = name;
            }

            public Address getAddress() {
                return address;
            }

            public void setAddress(Address address) {
                this.address = address;
            }

            public static class Name {
                private String fullName;

                public String getFullName() {
                    return fullName;
                }

                public void setFullName(String fullName) {
                    this.fullName = fullName;
                }
            }

            public static class Address {
                private String addressLine1;
                private String adminArea2;
                private String adminArea1;
                private String postalCode;
                private String countryCode;

                public String getAddressLine1() {
                    return addressLine1;
                }

                public void setAddressLine1(String addressLine1) {
                    this.addressLine1 = addressLine1;
                }

                public String getAdminArea2() {
                    return adminArea2;
                }

                public void setAdminArea2(String adminArea2) {
                    this.adminArea2 = adminArea2;
                }

                public String getAdminArea1() {
                    return adminArea1;
                }

                public void setAdminArea1(String adminArea1) {
                    this.adminArea1 = adminArea1;
                }

                public String getPostalCode() {
                    return postalCode;
                }

                public void setPostalCode(String postalCode) {
                    this.postalCode = postalCode;
                }

                public String getCountryCode() {
                    return countryCode;
                }

                public void setCountryCode(String countryCode) {
                    this.countryCode = countryCode;
                }
            }
        }

        public static class Payments {
            private List<Capture> captures;


            public static class Capture {
                private String id;
                private String status;
                private Amount amount;
                private boolean finalCapture;
                private SellerProtection sellerProtection;
                private SellerReceivableBreakdown sellerReceivableBreakdown;
                private List<Link> links;
                private String createTime;
                private String updateTime;


                public static class Amount {
                    private String currencyCode;
                    private String value;
                }


                public static class SellerProtection {
                    private String status;
                    private List<String> disputeCategories;
                }


                public static class SellerReceivableBreakdown {
                    private Amount grossAmount;
                    private Amount paypalFee;
                    private Amount netAmount;
                    private Amount receivableAmount;
                    private ExchangeRate exchangeRate;


                    public static class ExchangeRate {
                        private String sourceCurrency;
                        private String targetCurrency;
                        private String value;
                    }
                }
            }
        }
    }


    public static class Payer {
        private Name name;
        private String emailAddress;
        private String payerId;
        private Address address;


        public static class Name {
            private String givenName;
            private String surname;
        }


        public static class Address {
            private String countryCode;
        }
    }


    public static class Link {
        private String href;
        private String rel;
        private String method;
    }
}
